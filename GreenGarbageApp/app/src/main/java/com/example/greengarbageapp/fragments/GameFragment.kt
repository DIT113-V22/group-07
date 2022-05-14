package com.example.greengarbageapp.fragments

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.greengarbageapp.databinding.FragmentGameBinding
import com.example.greengarbageapp.mqtt.MqttSmartcar
import com.example.joystickjhr.JoystickJhr



class GameFragment : Fragment() {

    private var STRAIGHT_ANGLE = 0
    private var forward = 0
    private var backwards = 0
    private val limitBack = 30
    private val limitForward = 100
    private var turnR = 0
    private var turnL = 0
    private var STOP = 7 // For Arduino switch case "7"
    private var lastDirection = 0



    private var control: MqttSmartcar? = null

    @SuppressLint("ClickableViewAccessibility")
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
            binding.distance, binding.joystick) // ID i xml filen
        control!!.connectToMqttBroker()

        val joystick = binding.joystick
        joystick.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                with(joystick) {
                    move(event);
                    joyX();
                    joyY();
                    angle();
                    distancia()
                };

                val direction = joystick.direccion;

                if (lastDirection != direction) {
                    lastDirection = direction;
                    if (direction == JoystickJhr.STICK_UP) {
                        control!!.drive(increase(2),  STRAIGHT_ANGLE.toString(),  "Moving forward") }
                } else if (direction == JoystickJhr.STICK_UPRIGHT) {
                } else if (direction == JoystickJhr.STICK_RIGHT) {
                    control!!.drive(increase(6), increase(5), "Moving forward right") // does not increase speed
                } else if (direction == JoystickJhr.STICK_NONE) {
                    control!!.drive(STOP.toString(), STRAIGHT_ANGLE.toString(), "Stopping")
                } else if (direction == JoystickJhr.STICK_DOWNRIGHT) {
                } else if (direction == JoystickJhr.STICK_DOWN) {
                    control!!.drive(increase(3), STRAIGHT_ANGLE.toString(), "Moving backward")
                } else if (direction == JoystickJhr.STICK_DOWNLEFT) {
                } else if (direction == JoystickJhr.STICK_LEFT) {
                    control!!.drive(increase(6), increase(4), "Moving forward left") // does not increase speed
                } else if (direction == JoystickJhr.STICK_UPLEFT) {
                }
                return true

            }

        })
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