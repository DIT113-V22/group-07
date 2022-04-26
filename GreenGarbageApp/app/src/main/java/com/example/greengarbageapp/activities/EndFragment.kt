package com.example.greengarbageapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greengarbageapp.databinding.FragmentEndBinding



class EndFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentEndBinding.inflate(inflater, container, false)
        return binding.root
    }
    }