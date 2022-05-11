package com.example.greengarbageapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greengarbageapp.databinding.FragmentUNinfoBinding

class UNinfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentUNinfoBinding.inflate(inflater, container, false)
        binding.UNLinkTv.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://sdgs.un.org/goals/goal12")
            startActivity(openURL)
        }
        return binding.root
    }
}