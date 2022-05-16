package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.greengarbageapp.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {

    private var reviewAdded: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding = FragmentReviewBinding.inflate(inflater, container, false)

        val inputReview = binding.Rbutton
        val rateStars = binding.rBar
        val textAfterReview = binding.tyText

        inputReview.setOnClickListener(){
            reviewAdded = true
            inputReview?.setVisibility(View.GONE)
            rateStars?.setVisibility(View.GONE)
            textAfterReview?.text = "Thanks for your feedback!"
        }
        return binding.root
    }
}