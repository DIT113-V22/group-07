package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greengarbageapp.R
import com.example.greengarbageapp.database.Player
import com.example.greengarbageapp.database.PlayerViewModel
import com.example.greengarbageapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding = FragmentStartBinding.inflate(inflater, container, false)


        binding.buttonStart.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_introFragment))

        return binding.root
    }

    }
