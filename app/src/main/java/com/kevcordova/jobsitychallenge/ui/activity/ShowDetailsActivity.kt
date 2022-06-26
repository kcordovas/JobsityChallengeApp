package com.kevcordova.jobsitychallenge.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.kevcordova.jobsitychallenge.R
import com.kevcordova.jobsitychallenge.databinding.ActivityShowDetailsBinding
import com.kevcordova.jobsitychallenge.ui.activity.presenter.ShowDetailsViewModel

class ShowDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowDetailsBinding

    private val viewModel by lazy {
        ShowDetailsViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_details)
        binding.activity = this
    }

    fun addToFavoriteShow() {
    }

    fun backPage() {

    }
}