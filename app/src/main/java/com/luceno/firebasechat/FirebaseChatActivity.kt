package com.luceno.firebasechat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.luceno.firebasechat.ui.theme.FirebaseChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirebaseChatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            FirebaseChatTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FirebaseChatApp()
                }
            }
        }
        notificationSubscribe()
    }

    private fun notificationSubscribe(){
        Firebase.messaging.subscribeToTopic("chat")
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.i("TAG", "Subscribed notification topic chat")
                }else{
                    Log.w("TAG", "Subscribe failed!!, from notification topic chat")
                }
            }
    }
}
