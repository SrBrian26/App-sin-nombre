package com.example.parquepumalinapp.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}