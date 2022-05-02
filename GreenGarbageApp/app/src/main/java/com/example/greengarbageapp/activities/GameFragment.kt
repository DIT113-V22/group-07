package com.example.greengarbageapp.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar

class GameFragment : Fragment() {

    private val MOVEMENT_SPEED = 70
    private val IDLE_SPEED = 0
    private val STRAIGHT_ANGLE = 0
    private val STEERING_ANGLE = 50

    private var control: MqttSmartcar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val binding = FragmentGameBinding.inflate(inflater, container, false)
        control = MqttSmartcar(context, binding.imageView)
        control!!.connectToMqttBroker()
        val backward = binding.backward
        val forward = binding.forward
        val stop = binding.stop
        val left = binding.left
        val right = binding.right
        backward.setOnClickListener{
            control!!.drive(-MOVEMENT_SPEED, STRAIGHT_ANGLE, "Moving backward")
        }
        forward.setOnClickListener{
            control!!.drive(MOVEMENT_SPEED, STRAIGHT_ANGLE, "Moving forward")
        }
        stop.setOnClickListener{
            control!!.drive(IDLE_SPEED, STRAIGHT_ANGLE, "Stopping")
        }
        left.setOnClickListener{
            control!!.drive(MOVEMENT_SPEED, -STEERING_ANGLE, "Moving forward left")
        }
        right.setOnClickListener{
            control!!.drive(MOVEMENT_SPEED, STEERING_ANGLE, "Moving forward right")
        }
            return binding.root
        }
    }