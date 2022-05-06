package com.example.greengarbageapp.mqtt

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.eclipse.paho.client.mqttv3.*

class MqttSmartcar : AppCompatActivity {

    private val TAG = "SmartcarMqttController"
    private val LOCALHOST = "10.0.2.2"
    private val MQTT_SERVER = "tcp://$LOCALHOST:1883"
    private val THROTTLE_CONTROL = "/smartcar/control/takeInput"
    private val STEERING_CONTROL = "/smartcar/control/takeInput"
    private val QOS = 1
    private val IMAGE_WIDTH = 320
    private val IMAGE_HEIGHT = 240
    private var mTextView: TextView? = null
    private var mMqttClient: MqttClient? = null
    private var isConnected = false
    private var mCameraView: ImageView? = null

    private var context: Context? = null

    constructor(context: Context?, mCameraView: ImageView?, mTextView: TextView?) {
        mMqttClient = MqttClient(context, MQTT_SERVER, TAG)
        this.mCameraView = mCameraView
        this.context = context
        this.mTextView = mTextView
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
                        mTextView?.setText(speed)
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

    fun drive(throttleSpeed: String, steeringAngle: String, actionDescription: String?) {
        if (!isConnected) {
            val notConnected = "Not connected (yet)"
            Log.e(TAG, notConnected)
            return
        }
        Log.i(TAG, actionDescription!!)
        mMqttClient?.publish(THROTTLE_CONTROL, throttleSpeed, QOS, null)
        mMqttClient?.publish(STEERING_CONTROL, steeringAngle, QOS, null)
    }
}