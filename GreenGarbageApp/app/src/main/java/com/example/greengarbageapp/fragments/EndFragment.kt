package com.example.greengarbageapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.greengarbageapp.R
import com.example.greengarbageapp.database.Player
import com.example.greengarbageapp.database.PlayerViewModel
import com.example.greengarbageapp.databinding.FragmentEndBinding

class EndFragment : Fragment() {

    private lateinit var mPlayerViewModel: PlayerViewModel
    private val args by navArgs<EndFragmentArgs>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentEndBinding.inflate(inflater, container, false)
        mPlayerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]


        binding.distanceEnd.text = args.distance.toString()
        binding.pointsEnd.text = args.points.toString()

        binding.addScore.setOnClickListener {
            if (checkInputName(binding) && checkInputReview(binding)) {
                val username = binding.usernameEnd.text.toString()
                val distance = binding.distanceEnd.text.toString().toInt()
                val points = binding.pointsEnd.text.toString().toInt()
                val reviews = binding.userReview.text.toString().toInt()

                addToDatabase(username, distance, points, reviews)
                binding.addScore.setVisibility(View.GONE)
                Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()

            }else {
                Toast.makeText(requireContext(), "Please fill out all blanks", Toast.LENGTH_SHORT).show()
            }
        }

       binding.leaderboard.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_endFragment_to_leaderboardFragment))
        binding.homeEnd.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_endFragment_to_startFragment))
        return binding.root
    }
    fun addToDatabase(username: String, distance: Int, points: Int, review : Int){
        val player = Player(0, username, distance, points, review)
        mPlayerViewModel.addPlayer(player)
    }


    private fun checkInputName(binding: FragmentEndBinding): Boolean {
        val userName = binding.usernameEnd.text.toString()
        if (userName.isEmpty()){
            return false }
        return true
    }

    private fun checkInputReview(binding: FragmentEndBinding) : Boolean{
        val reviews = binding.userReview.text.toString()
        if(reviews.isEmpty()){
            return false
        }
        val number = reviews.toInt()
        if( number < 0  || number > 5) {
            Toast.makeText(requireContext(), "Please put numbers between 1 and 5", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }




}