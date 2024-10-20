package com.example.parquepumalinapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.style.BackgroundColorSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        splash()
    }
    private fun splash() {
        object : CountDownTimer(1000,100){
            override fun onTick(p0: Long) {

            }
            override fun onFinish() {
                val pantalla = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(pantalla)
            }
        }.start()
    }
}