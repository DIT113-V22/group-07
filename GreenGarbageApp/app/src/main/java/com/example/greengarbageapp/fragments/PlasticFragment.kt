package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greengarbageapp.databinding.FragmentPlasticBinding

class PlasticFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val binding = FragmentPlasticBinding.inflate(inflater, container, false)

            return binding.root
        }
}