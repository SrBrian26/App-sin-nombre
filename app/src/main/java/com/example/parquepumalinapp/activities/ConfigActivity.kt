package com.example.parquepumalinapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.parquepumalinapp.databinding.ActivityConfigBinding

class ConfigActivity : AppCompatActivity() {
    lateinit var binding: ActivityConfigBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarConfig.title = "Configuraciones"
        val toolbar = binding.toolbarConfig
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { //Realiza una acción dependiendo de la opción seleccionada
        return when (item.itemId) {
            android.R.id.home -> { //Al presionar la flecha volver envia al Menu
                val pantalla = Intent(this, MenuPrincipal::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}