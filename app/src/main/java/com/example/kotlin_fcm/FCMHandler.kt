package com.example.kotlin_fcm

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException


class FCMHandler {

    fun enableFCM() {
        // Enable FCM via enable Auto-init service which generate new token and receive in FCMService
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    fun disableFCM() {
        // Try to remove InstanceID initiate to unsubscribe all topic
        // TODO: May be a better way to use FirebaseMessaging.getInstance().unsubscribeFromTopic()
        Thread {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()

        // Disable FCM auto init
        FirebaseMessaging.getInstance().isAutoInitEnabled = false
    }
}