package com.example.greengarbageapp.fragments

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar
import io.github.controlwear.virtual.joystick.android.JoystickView


class GameFragment : Fragment() {

    private var STRAIGHT_ANGLE = 0
    private var REVERSE = -1
    private var currentSpeed= 0
    private var currentAngle = 0
    private var control: MqttSmartcar? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding = FragmentGameBinding.inflate(inflater, container, false)

        control = MqttSmartcar(context, binding.cameraViewIv, binding.speedometerIndicatorTv, binding.distance, binding.joystickViewLeft, binding.count)
        control!!.connectToMqttBroker()

        binding.endGame.setOnClickListener {
            val points = binding.count.text.toString().toInt()
            val distance = binding.distance.text.toString().toInt()
            val action = GameFragmentDirections.actionGameFragmentToEndFragment(distance, points)
            findNavController().navigate(action)
        }

        //joystick is combined version from https://github.com/controlwear/virtual-joystick-android
        val joystick = binding.joystickViewLeft
        joystick.setOnMoveListener(object : JoystickView.OnMoveListener {
            override fun onMove(angle: Int, strength: Int) {
                val speedGo: Int
                var angleGo: Int
                if (angle in 90..180) {
                    speedGo = turnF(angle)
                    angleGo = driveF(strength)
                } else if (angle in 0..89) {
                    speedGo = turnF(angle)
                    angleGo = driveF(strength)
                } else if (angle > 0 && angle >= 270) {
                    speedGo = turnB(angle)
                    angleGo = driveB(strength)
                } else {
                    speedGo = turnB(angle)
                    angleGo = driveB(strength)
                }
                if (angleGo != currentAngle || speedGo != currentSpeed) {
                    if (speedGo == 0) angleGo = 0
                    sendMovement(speedGo, angleGo)
                    currentAngle = angleGo
                    currentSpeed = speedGo

                }
            }

        })
        return binding.root
    }


    private fun driveF(strength: Int): Int {
        return (strength * 0.6).toInt()
    }

    private fun driveB(strength: Int): Int {
        return (strength * 0.2 * REVERSE).toInt()
    }

    private fun turnF(angle: Int): Int {
        return 90 - angle
    }

    private fun turnB(angle: Int): Int {
        return angle - 270
    }

    private fun sendMovement(newSpeed: Int, newAngle: Int) {
      control!!.drive(newSpeed, newAngle, "")
    }

}