package com.example.greengarbageapp.mqtt

import android.content.Context
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MqttClient(context: Context?, serverUrl: String?, clientId: String?) {

    private val mMqttAndroidClient: MqttAndroidClient =
        MqttAndroidClient(context, serverUrl, clientId)

    fun connect(
        username: String?,
        password: String,
        connectionCallback: IMqttActionListener?,
        clientCallback: MqttCallback?
    ) {
        mMqttAndroidClient!!.setCallback(clientCallback)
        val options = MqttConnectOptions()
        options.userName = username
        options.password = password.toCharArray()
        options.isAutomaticReconnect = true
        options.isCleanSession = true
        try {
            mMqttAndroidClient!!.connect(options, null, connectionCallback)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect(disconnectionCallback: IMqttActionListener?) {
        try {
            mMqttAndroidClient!!.disconnect(null, disconnectionCallback)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String?, qos: Int, subscriptionCallback: IMqttActionListener?) {
        try {
            mMqttAndroidClient!!.subscribe(topic, qos, null, subscriptionCallback)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String?, message: String, qos: Int, publishCallback: IMqttActionListener?) {
        val mqttMessage = MqttMessage()
        mqttMessage.payload = message.toByteArray()
        mqttMessage.qos = qos
        try {
            mMqttAndroidClient!!.publish(topic, mqttMessage, null, publishCallback)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

}