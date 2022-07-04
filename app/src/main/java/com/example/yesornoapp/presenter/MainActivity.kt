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
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

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
        setContentView(R.layout.activity_main)

        (applicationContext as App).appComponent.inject(this)

        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        setupObservers()
        //setUpButtons()
        updateAnswer()
    }

    private fun setLoadingVisibility(isLoad: Boolean) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val mainGif = findViewById<ImageView>(R.id.main_gif)
        val mainText = findViewById<TextView>(R.id.main_text)

        progressBar.visibility = if (isLoad) View.VISIBLE else View.GONE
        mainGif.visibility = if (isLoad) View.GONE else View.VISIBLE
        mainText.visibility = if (isLoad) View.GONE else View.VISIBLE
    }

    private fun setAnswer(answer: Answer) {
        val mainGif = findViewById<ImageView>(R.id.main_gif)
        val mainText = findViewById<TextView>(R.id.main_text)

        mainText.text = answer.answer
        Glide
            .with(this)
            .load(answer.image)
            .into(mainGif)
    }

    private fun updateAnswer(){
        viewModel.getAnswer()
    }

    private fun setupObservers() {
        viewModel.getAnswer().observe(this, answerObserver)
    }

    private fun  showErrorMessage(error:String){
        val mainText = findViewById<TextView>(R.id.main_text)
        mainText.text = error
    }

//    private fun setUpButtons(){
//        val button = findViewById<Button>(R.id.reload_button)
//        button.setOnClickListener {
//            updateAnswer()
//        }
//    }

}