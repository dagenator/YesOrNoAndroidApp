package com.example.yesornoapp.presenter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yesornoapp.core.app.App
import com.example.yesornoapp.core.db.AppDatabase
import com.example.yesornoapp.core.Resource
import com.example.yesornoapp.core.Status
import com.example.yesornoapp.data.factory.ViewModelFactory
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var db: AppDatabase

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    private var answerObserver: Observer<Resource<Answer>> = Observer { resource ->
        resource?.let { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    setLoadingVisibility(false)
                    resource.data?.let {
                        setAnswer(it)
                    }
                }
                Status.ERROR -> {
                    setLoadingVisibility(false)
                    resource.data?.let {
                        setAnswer(it)
                    }
                    resource.message?.let {
                        showErrorMessage(true, it)
                    }
                }
                Status.LOADING -> {
                    setLoadingVisibility(true)
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        updateAnswer()
        setUpButtons()
    }

    private fun setLoadingVisibility(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.mainGif.visibility = if (isLoad) View.GONE else View.VISIBLE
        binding.mainText.visibility = if (isLoad) View.GONE else View.VISIBLE
    }

    private fun setAnswer(answer: Answer) {
        binding.mainText.text = answer.answer
        try {
            Glide
                .with(this)
                .load(answer.image)
                .into(binding.mainGif)
        } catch (e: Exception) {
            showErrorMessage(true, e.message.toString())
        }

    }

    private fun updateAnswer() {
        setupObservers() // почему обсервер сбрасывается если его сетать только в onCreate?
        viewModel.getAnswer()
    }

    private fun setupObservers() {
        viewModel.getAnswer().observe(this, answerObserver)

    }

    private fun showErrorMessage(show: Boolean, error: String = "null") {
        binding.errorText?.let {
            it.text = error
            it.visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    private fun setUpButtons() {
        binding.reloadButton?.let {
            it.setOnClickListener {
                updateAnswer()
            }
        }
    }

}