package com.example.greengarbageapp.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.example.greengarbageapp.R
import com.example.greengarbageapp.mqtt.MqttSmartcar


class GameActivity : AppCompatActivity(){

    private val MOVEMENT_SPEED = 70
    private val IDLE_SPEED = 0
    private val STRAIGHT_ANGLE = 0
    private val STEERING_ANGLE = 50

    private var control: MqttSmartcar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = findViewById<ImageView>(R.id.imageView)
        control = MqttSmartcar(this.applicationContext, imageView)
        control!!.connectToMqttBroker()
    }

    fun moveForward(view: View?) {
        control?.drive(MOVEMENT_SPEED, STRAIGHT_ANGLE, "Moving forward")
    }

    fun moveForwardLeft(view: View?) {
        control?.drive(MOVEMENT_SPEED, -STEERING_ANGLE, "Moving forward left")
    }

    fun stop(view: View?) {
        control?.drive(IDLE_SPEED, STRAIGHT_ANGLE, "Stopping")
    }

    fun moveForwardRight(view: View?) {
        control?.drive(MOVEMENT_SPEED, STEERING_ANGLE, "Moving forward left")
    }

    fun moveBackward(view: View?) {
        control?.drive(-MOVEMENT_SPEED, STRAIGHT_ANGLE, "Moving backward")
    }

}