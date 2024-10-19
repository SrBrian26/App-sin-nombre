package com.example.parquepumalinapp.activities.InfoQR

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.databinding.ActivityInfoQrCampingBinding

class InfoQrCampingActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoQrCampingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoQrCampingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}