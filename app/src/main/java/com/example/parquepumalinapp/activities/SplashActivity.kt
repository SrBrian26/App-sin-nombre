package com.example.parquepumalinapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.R

@SuppressLint("CustomSplashScreen")
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
                val pantalla = Intent(this@SplashActivity, MenuPrincipal::class.java)
                startActivity(pantalla)
            }
        }.start()
    }
}