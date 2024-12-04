package com.smart.task.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.smart.task.R
import com.smart.task.ui.main.MainActivity

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set your custom splash layout
        setContentView(R.layout.activity_splash)

        // Simulate loading or initialize your app (e.g., loading configurations, etc.)
        Handler(Looper.getMainLooper()).postDelayed({
            // Start the main activity
            startActivity(Intent(this, MainActivity::class.java))
            // Close this activity
            finish()
        }, 2000) // 2-second delay
    }
}