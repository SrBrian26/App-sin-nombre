package com.example.parquepumalinapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.databinding.ActivityAcercaDeBinding

class AcercaDeActivity : AppCompatActivity() {
    lateinit var binding: ActivityAcercaDeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAcercaDeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}