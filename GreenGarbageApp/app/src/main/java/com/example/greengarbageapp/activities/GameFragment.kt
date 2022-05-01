package com.example.greengarbageapp.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar


class GameFragment : Fragment() {

    private val STRAIGHT_ANGLE = 0
    private val STEERING_ANGLE = 50
    private var forward = 0
    private var backwards = 0
    private val limitBack = 30
    private val limitForward = 100


    private var control: MqttSmartcar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater, container, false)
        control = MqttSmartcar(context, binding.imageView)
        control!!.connectToMqttBroker()
        val backward = binding.backward
        val forward = binding.forward
        val stop = binding.stop
        val left = binding.left
        val right = binding.right
        backward.setOnClickListener {
            control!!.drive(increment(3), STRAIGHT_ANGLE, "Moving backward")
        }
        forward.setOnClickListener {
            control!!.drive(increment(2), STRAIGHT_ANGLE, "Moving forward")
        }
        /*
        stop.setOnClickListener {
            control!!.drive(IDLE_SPEED, STRAIGHT_ANGLE, "Stopping")
        }
        left.setOnClickListener {
            control!!.drive(MOVEMENT_SPEED, -STEERING_ANGLE, "Moving forward left")
        }
        right.setOnClickListener {
            control!!.drive(MOVEMENT_SPEED, STEERING_ANGLE, "Moving forward right")
        }

         */
        return binding.root
    }

    fun increment(pointer: Int): String {
        val INCREMENT_BY = 10
        var result = ""
        when (pointer) {
            2 -> {
                if (forward !== limitForward) {
                    forward += INCREMENT_BY
                }
                result = "2 $forward"
            }
            3 -> {

                if (backwards !== limitBack) {
                    backwards += INCREMENT_BY
                }
                result = "3 $backwards"
            }
        }
        return result
    }
}