package com.example.greengarbageapp.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar
import com.google.zxing.integration.android.IntentIntegrator


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding = FragmentGameBinding.inflate(inflater, container, false)
        
        control = MqttSmartcar(
            context,
            binding.cameraViewIv,
            binding.speedometerIndicatorTv,
            binding.distance
        ) // ID i xml filen

        control!!.connectToMqttBroker()
        val backward = binding.backward
        val forward = binding.forward
        val stop = binding.stop
        val left = binding.left
        val right = binding.right
        backward.setOnClickListener {
            control!!.drive(increase(3), STRAIGHT_ANGLE.toString(), "Moving backward")
        }
        forward.setOnClickListener {
            control!!.drive(increase(2), STRAIGHT_ANGLE.toString(), "Moving forward")
        }

        stop.setOnClickListener {
            control!!.drive(STOP.toString(), STRAIGHT_ANGLE.toString(), "Stopping")
        }

        left.setOnClickListener {
            control!!.drive(
                increase(6),
                increase(4),
                "Moving forward left"
            ) // does not increase speed
        }
        right.setOnClickListener {
            control!!.drive(
                increase(6),
                increase(5),
                "Moving forward right"
            ) // does not increase speed
        }
        // QR code scan
        val qrButton = binding.qrButton
        qrButton.setOnClickListener{
            val intentIntegrator = IntentIntegrator(activity)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
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