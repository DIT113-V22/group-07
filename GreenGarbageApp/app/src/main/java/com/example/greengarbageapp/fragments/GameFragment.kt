package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greengarbageapp.R
import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar


class GameFragment : Fragment() {

    private var STRAIGHT_ANGLE = 0
    private var forward = 0
    private var backwards = 0
    private val limitBack = 30
    private val limitForward = 100
    private var turnR = 0
    private var turnL = 0
    private var STOP = 7 // For Arduino switch case "7"


    private var control: MqttSmartcar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding = FragmentGameBinding.inflate(inflater, container, false)
        val blink = binding.blink

        control = MqttSmartcar(
            context,
            binding.cameraViewIv,
            binding.speedometerIndicatorTv,
            binding.distance,
            binding.count
        )
        control!!.connectToMqttBroker()


        binding.endGame.setOnClickListener {
            val points = 0
            val distance = binding.distance.text.toString().toInt()
            val action = GameFragmentDirections.actionGameFragmentToEndFragment(distance, points)
            findNavController().navigate(action)
        }

        val backward = binding.backward
        val forward = binding.forward
        val stop = binding.stop
        val left = binding.left
        val right = binding.right
        backward.setOnClickListener {
            control!!.drive(increase(3), STRAIGHT_ANGLE.toString(), "Moving backward")
            val anim = AnimationUtils.loadAnimation(context, R.anim.blink)
            blink.startAnimation(anim)
        }
        forward.setOnClickListener {
            control!!.drive(increase(2), STRAIGHT_ANGLE.toString(), "Moving forward")
            blink.setAnimation(null)
        }

        stop.setOnClickListener {
            control!!.drive(STOP.toString(), STRAIGHT_ANGLE.toString(), "Stopping")
            blink.setAnimation(null)
        }

        left.setOnClickListener {
            control!!.drive(
                increase(6), increase(4),"Moving forward left"
            )
        }
        right.setOnClickListener {
            control!!.drive(
                increase(6), increase(5),"Moving forward right"
            ) // does not increase speed
        }
        return binding.root
    }

    fun increase(pointer: Int): String {
        val INCREASE_BY = 10
        var result = ""
        when (pointer) {
            2 -> {
                if (forward != limitForward) {
                    forward += INCREASE_BY
                }
                result = "2 $forward"
            }
            3 -> {
                if (backwards != limitBack) {
                    backwards += INCREASE_BY
                }
                result = "3 $backwards"
            }
            4 -> {
                if (turnL != 0) {
                    turnL -= INCREASE_BY
                }
                turnR += INCREASE_BY
                result = "4 $turnR"
            }
            5 -> {
                if (turnR != 0) {
                    turnR -= INCREASE_BY
                }
                turnL += INCREASE_BY
                result = "5 $turnL"
            }
            6 -> {

                result = "6 $forward" // when turning
            }
        }
        return result
    }
}