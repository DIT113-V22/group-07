package com.example.greengarbageapp.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greengarbageapp.databinding.FragmentAboutBinding
import com.example.greengarbageapp.databinding.FragmentUNinfoBinding

class UNinfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentUNinfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}