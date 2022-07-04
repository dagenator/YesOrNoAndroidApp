package com.example.yesornoapp.presenter

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.yesornoapp.R
import com.example.yesornoapp.core.app.App
import com.example.yesornoapp.data.Resource
import com.example.yesornoapp.data.Status
import com.example.yesornoapp.data.factory.ViewModelFactory
import com.example.yesornoapp.data.model.Answer
import com.example.yesornoapp.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
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
                    Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()

                    resource.message?.let {
                        showErrorMessage(it)
                        print(it)
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

        (applicationContext as App).appComponent.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        updateAnswer()
        setUpButtons()
        setContentView(binding.root)
    }

    private fun setLoadingVisibility(isLoad: Boolean) {
        binding.progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        binding.mainGif.visibility = if (isLoad) View.GONE else View.VISIBLE
        binding.mainText.visibility = if (isLoad) View.GONE else View.VISIBLE
    }

    private fun setAnswer(answer: Answer) {
        binding.mainText.text = answer.answer
        Glide
            .with(this)
            .load(answer.image)
            .into(binding.mainGif)
    }

    private fun updateAnswer(){
        setupObservers() // почему обсервер сбрасывается?
        viewModel.getAnswer()
    }

    private fun setupObservers() {
        viewModel.getAnswer().observe(this, answerObserver)
    }

    private fun  showErrorMessage(error:String){
        binding.mainText.text = error
    }

    private fun setUpButtons(){
        binding.reloadButton?.let{
            it.setOnClickListener {
                updateAnswer()
            }
        }
    }
}