package com.example.kotlin_fcm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnCopy = findViewById<Button>(R.id.btnCopy)
        val txtFCMToken = findViewById<TextView>(R.id.txtFCMToken)

        var token: String? = null

        btnLogin.setOnClickListener {
            FCMHandler().enableFCM()

            // retrieve current FCM Token
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.i("MYTAG", "Fetching FCM Registration token failed")
                    return@OnCompleteListener
                }

                token = task.result
                txtFCMToken.text = token
            })
        }

        btnLogout.setOnClickListener {
            FCMHandler().disableFCM()

            val disabledText = getString(R.string.disabled_fcm_token)
            txtFCMToken.text = getString(R.string.msg_token_fmt, disabledText)
        }

        btnCopy.setOnClickListener {
            val clipManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("FCM Token", token)
            clipManager.setPrimaryClip(clipData)

            Toast.makeText(this, getString(R.string.copied_clipboard), Toast.LENGTH_LONG).show()
        }
    }
}