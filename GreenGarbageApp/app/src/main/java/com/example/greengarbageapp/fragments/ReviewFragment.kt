package com.example.greengarbageapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greengarbageapp.database.ReviewAdapter
import com.example.greengarbageapp.database.PlayerViewModel
import com.example.greengarbageapp.databinding.FragmentReviewBinding

class ReviewFragment : Fragment() {
    private lateinit var mPlayerViewModel: PlayerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentReviewBinding.inflate(inflater, container, false)
        val adapter = ReviewAdapter()
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mPlayerViewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        mPlayerViewModel.readAllData.observe(viewLifecycleOwner) { player ->
            adapter.setData(player)
        }
        return binding.root

    }


}