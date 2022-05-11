package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.greengarbageapp.R
import com.example.greengarbageapp.database.Player
import com.example.greengarbageapp.database.PlayerViewModel
import com.example.greengarbageapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    private lateinit var mPlayerViewModel: PlayerViewModel
    private var userNameAdded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mPlayerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
        val binding = FragmentStartBinding.inflate(inflater, container, false)
        if (userNameAdded){
            val inputUserName = binding.userName
            inputUserName?.setVisibility(View.GONE)
            val addButton = binding.buttonAdd
            addButton?.setVisibility(View.GONE)
            // Here we could also show the users name in an un-editable manner
        }
        binding.buttonStart.setOnClickListener {
            navigateToIntro(binding)
        }
        binding.buttonAdd?.setOnClickListener{
            addPlayerToDatabase(binding)
        }
        return binding.root
    }

    private fun addPlayerToDatabase(binding: FragmentStartBinding) {
        val text = binding.userName
        val button = binding.buttonAdd
        val userName = binding.userName!!.text.toString()
        if (userName.isNotEmpty()) {
            val player = Player(0, userName, 0.0, 0)
            mPlayerViewModel.addPlayer(player)
            userNameAdded = true
            text?.setVisibility(View.GONE)
            button?.setVisibility(View.GONE)
        } else {
            Toast.makeText(requireContext(), "Please fill in a username", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToIntro(binding: FragmentStartBinding) {
        val userName = binding.userName!!.text.toString()
        if (userName.isNotEmpty() && userNameAdded){
            findNavController().navigate(R.id.action_startFragment_to_introFragment)
        } else if (userName.isNotEmpty() && !userNameAdded){
            Toast.makeText(requireContext(), "Please add your username", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Please fill in a username", Toast.LENGTH_SHORT).show()
        }
    }

}
