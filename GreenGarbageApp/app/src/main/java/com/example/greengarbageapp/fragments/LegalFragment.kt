package com.example.greengarbageapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greengarbageapp.databinding.FragmentLegalBinding

class LegalFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentLegalBinding.inflate(inflater, container, false)
        return binding.root
    }
}