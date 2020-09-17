package com.example.wellstest.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellstest.R
import com.example.wellstest.data.NetworkRepo
import com.example.wellstest.databinding.ActivityMainBinding
import com.example.wellstest.model.State
import com.example.wellstest.utils.ViewModelFactory
import com.example.wellstest.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory(NetworkRepo())
    }

    private lateinit var binding: ActivityMainBinding

    private var newsAdapter = NewsAdapter {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        lifecycle.addObserver(viewModel)

        binding.rcNews.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }

        setUpObservers()
    }

    /**
     *
     */
    private fun setUpObservers() {
        viewModel.newsResponse.observe(this, Observer {
            newsAdapter.setData(it.articles)
        })

        viewModel.apiStateEvent.observe(this, Observer {
            when (it) {
                is State.Success -> {
                    // Handle the success for all common api's
                }
                is State.Error -> {
                    // Handle the error
                }
                is State.Loading -> {
                    if (it.showProgress) {
                        // Show the loading progress
                    } else {
                        // Dismiss the loading progress
                    }
                }
            }
        })
    }
}
