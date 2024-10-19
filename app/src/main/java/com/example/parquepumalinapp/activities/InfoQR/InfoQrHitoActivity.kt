package com.example.parquepumalinapp.activities.InfoQR

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.databinding.ActivityInfoQrHitoBinding

class InfoQrHitoActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoQrHitoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoQrHitoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}