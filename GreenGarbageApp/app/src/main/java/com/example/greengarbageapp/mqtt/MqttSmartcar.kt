package com.example.greengarbageapp.mqtt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.controlwear.virtual.joystick.android.JoystickView
import org.eclipse.paho.client.mqttv3.*
import kotlin.math.roundToInt

class MqttSmartcar : AppCompatActivity {

    private val TAG = "SmartcarMqttController"
    private val LOCALHOST = "10.0.2.2"
    private val MQTT_SERVER = "tcp://$LOCALHOST:1883"
    private val THROTTLE_CONTROL = "/smartcar/control/throttle"
    private val STEERING_CONTROL = "/smartcar/control/steering"
    private val QOS = 1
    private val IMAGE_WIDTH = 320
    private val IMAGE_HEIGHT = 240
    private var speedometer: TextView? = null
    private var mMqttClient: MqttClient? = null
    private var count : TextView?=null
    private var isConnected = false
    private var mCameraView: ImageView? = null
    private var joystick: JoystickView? = null
    private var context: Context? = null
    private var distance: TextView? = null

    constructor(context: Context?, mCameraView: ImageView?, speedometer: TextView?, distance: TextView?, joystick: JoystickView, count: TextView?) {
        mMqttClient = MqttClient(context, MQTT_SERVER, TAG)
        this.mCameraView = mCameraView
        this.context = context
        this.speedometer = speedometer
        this.distance = distance
        this.joystick = joystick
        this.count = count
    }
    constructor(){
    }
    override fun onResume() {
        super.onResume()
        connectToMqttBroker()
    }

    override fun onPause() {
        super.onPause()
        mMqttClient?.disconnect(object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken) {
                Log.i(TAG, "Disconnected from broker")
            }

            override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {}
        })
    }

    fun connectToMqttBroker() {
        if (!isConnected) {
            mMqttClient?.connect(TAG, "", object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    isConnected = true
                    val successfulConnection = "Connected to MQTT broker"
                    Log.i(TAG, successfulConnection)
                    mMqttClient?.subscribe("/smartcar/ultrasound/front", QOS, null)
                    mMqttClient?.subscribe("/smartcar/camera", QOS, null)
                    mMqttClient?.subscribe("/smartcar/speedometer", QOS, null)
                    mMqttClient?.subscribe("/smartcar/distance", QOS, subscriptionCallback = null)
                    mMqttClient?.subscribe("/smartcar/count", QOS, null)
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    val failedConnection = "Failed to connect to MQTT broker"
                    Log.e(TAG, failedConnection)
                }
            }, object : MqttCallback {
                override fun connectionLost(cause: Throwable) {
                    isConnected = false
                    val connectionLost = "Connection to MQTT broker lost"
                    Log.w(TAG, connectionLost)
                }

                @Throws(Exception::class)
                override fun messageArrived(topic: String, message: MqttMessage) {
                    if (topic == "/smartcar/camera") {
                        val bm =
                            Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888)
                        val payload = message.payload
                        val colors = IntArray(IMAGE_WIDTH * IMAGE_HEIGHT)
                        for (ci in colors.indices) {
                            val r: Int = payload[3 * ci].toInt() and 0xFF
                            val g: Int = payload[3 * ci + 1].toInt() and 0xFF
                            val b: Int = payload[3 * ci + 2].toInt() and 0xFF
                            colors[ci] = Color.rgb(r, g, b)
                        }
                        bm.setPixels(colors, 0, IMAGE_WIDTH, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT)
                        mCameraView!!.setImageBitmap(bm)

                    } else if(topic == "/smartcar/speedometer"){
                        val speed = message.toString()
                        val speedNumb = speed.toDouble()
                        val speedInKm = speedNumb * 3.6
                        val testSpeed = Math.round(speedInKm * 100.0) / 100.00
                        val speedDisplay = testSpeed.toString() + "km/h"
                        speedometer?.text = speedDisplay
                    } else if(topic == "/smartcar/distance"){
                        val mDistance = message.toString()
                        distance?.text = mDistance
                    } else if(topic == "/smartcar/count"){
                        val mCount = message.toString()
                        count?.text = mCount
                    }else{
                        Log.i(TAG, "[MQTT] Topic: $topic | Message: $message")
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken) {
                    Log.d(TAG, "Message delivered")
                }
            })
        }
    }

    fun drive(throttleSpeed: Int, steeringAngle: Int, actionDescription: String?) {
        if (!isConnected) {
            val notConnected = "Not connected (yet)"
            Log.e(TAG, notConnected)
            return
        }
        Log.i(TAG, actionDescription!!)
        mMqttClient?.publish(THROTTLE_CONTROL, throttleSpeed.toString(), QOS, null)
        mMqttClient?.publish(STEERING_CONTROL, steeringAngle.toString(), QOS, null)
    }
}

