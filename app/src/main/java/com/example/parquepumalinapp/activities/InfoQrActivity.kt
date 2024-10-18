package com.example.parquepumalinapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.databinding.ActivityInfoQrBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import kotlinx.coroutines.launch

class InfoQrActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoQrBinding
    lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoQrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase()
        val id_QR = intent.getStringExtra("IDqr")

        if (id_QR != null){
            obtenerDatos(id_QR)
        } else {
            Toast.makeText(this@InfoQrActivity, "error al escanear", Toast.LENGTH_SHORT).show()
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    @SuppressLint("SetTextI18n") /*solucion temporal*/
    private fun obtenerDatos(Id: String){
        limpiarCampos()
        lifecycleScope.launch {
            try {
                val infoQr = room.InfoQrDao().getInfoQrById(Id)

                if (infoQr != null) {
                    binding.toolbar2.title = infoQr.NombreZona
                    binding.traerDesc.text = infoQr.Descripcion
                    binding.traerUbi.text = infoQr.Ubicacion
                    binding.traerSendero.text = infoQr.Sendero
                    binding.traerFauna.text = infoQr.infoFauna
                    comprobarImg(Id)
                } else {
                    Toast.makeText(this@InfoQrActivity, "No se encontraron datos para el ID proporcionado",
                        Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                e.message
            }
        }
    }
    private fun limpiarCampos(){
        binding.traerDesc.text = ""
        binding.traerUbi.text = ""
        binding.traerSendero.text = ""
        binding.traerTiempo.text = ""
        binding.traerFauna.text = ""
    }
    private fun comprobarImg(Id: String){
        when(Id){
            "1" -> binding.traerImg.setImageResource(R.drawable.parque)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                // Lógica para manejar la acción del ítem config
                true
            }
            R.id.action_acerca_de -> {
                // Lógica para manejar la acción del ítem acerca de
                true
            }
            android.R.id.home -> {
                val pantalla = Intent(this, MainActivity::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}