package com.example.greengarbageapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.greengarbageapp.R
import com.example.greengarbageapp.databinding.FragmentAboutBinding
import com.example.greengarbageapp.databinding.FragmentEndBinding


class AboutFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.githubLinkTv.setOnClickListener {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse("https://github.com/DIT113-V22/group-07")
                startActivity(openURL)
            }
            return binding.root
    }
}