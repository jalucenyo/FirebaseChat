package com.luceno.firebasechat.feature_notifications.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService(

): FirebaseMessagingService() {

    /**
     * Ignore notifications where app in foreground.
     */
    override fun onMessageReceived(message: RemoteMessage) {
        Log.i("TAG", "Notigication reviced: ${message.notification?.title}")
    }

}