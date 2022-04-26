package com.example.greengarbageapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.greengarbageapp.R
import com.example.greengarbageapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentStartBinding.inflate(inflater, container, false)
        binding.button1.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_startFragment_to_introFragment))
        return binding.root
    }

}
