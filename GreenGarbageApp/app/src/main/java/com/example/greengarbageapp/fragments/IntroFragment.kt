package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greengarbageapp.R

import com.example.greengarbageapp.databinding.FragmentIntroBinding


class IntroFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val binding =  FragmentIntroBinding.inflate(inflater, container, false)

        binding.buttonPlay.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_introFragment_to_gameFragment))

        return binding.root
    }
}