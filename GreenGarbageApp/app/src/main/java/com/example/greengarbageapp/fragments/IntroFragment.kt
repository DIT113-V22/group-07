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

        //Can
        super.onCreateView(inflater, container, savedInstanceState)
        binding.canIb?.setOnClickListener{
            val dialog = CanFragment()
            dialog.show(childFragmentManager,"customDialog")
        }
        //Plastic
        super.onCreateView(inflater, container, savedInstanceState)
        binding.plasticIb?.setOnClickListener{
            val dialog = PlasticFragment()
            dialog.show(childFragmentManager,"customDialog")
        }
        //Food waste
        super.onCreateView(inflater, container, savedInstanceState)
        binding.appleIb?.setOnClickListener{
            val dialog = FoodWasteFragment()
            dialog.show(childFragmentManager,"customDialog")
        }
        //Glass
        super.onCreateView(inflater, container, savedInstanceState)
        binding.bottleIb?.setOnClickListener{
            val dialog = GlassFragment()
            dialog.show(childFragmentManager,"customDialog")
        }
        //Paper
        super.onCreateView(inflater, container, savedInstanceState)
        binding.paperIb?.setOnClickListener{
            val dialog = PaperFragment()
            dialog.show(childFragmentManager,"customDialog")
        }

        return binding.root
    }
}