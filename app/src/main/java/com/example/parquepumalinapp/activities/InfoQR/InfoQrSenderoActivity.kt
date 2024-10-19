package com.example.parquepumalinapp.activities.InfoQR

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
import com.example.parquepumalinapp.activities.AcercaDeActivity
import com.example.parquepumalinapp.activities.ConfigActivity
import com.example.parquepumalinapp.activities.MainActivity
import com.example.parquepumalinapp.databinding.ActivityInfoQrSenderoBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import kotlinx.coroutines.launch

class InfoQrSenderoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoQrSenderoBinding
    lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoQrSenderoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase()
        val id_QR = intent.getStringExtra("IDqr")

        if (id_QR != null){
            obtenerDatos(id_QR)
        } else {
            Toast.makeText(this@InfoQrSenderoActivity, "error al escanear",
                Toast.LENGTH_SHORT).show()
        }
        val toolbar = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    private fun obtenerDatos(Id: String){
        limpiarCampos()
        lifecycleScope.launch {
            try {
                val infoQr = room.InfoQrDao().getQRSenderoById(Id)

                if (infoQr != null) {
                    binding.toolbar2.title = infoQr.Nombre_Sendero
                    binding.toolbar2.subtitle = infoQr.Sector_Sendero
                    binding.traerDesc.text = infoQr.Descripcion_Sendero
                    binding.traerDificultad.text = infoQr.Dificultad_Sendero
                    binding.traerLongitud.text = infoQr.Longitud_Sendero
                    binding.traerFlora.text = infoQr.Flora_Sendero
                    comprobarImg(Id)
                } else {
                    Toast.makeText(this@InfoQrSenderoActivity, "No se encontraron " +
                            "datos para el ID proporcionado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this@InfoQrSenderoActivity, "error: " +
                        e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun limpiarCampos(){
        binding.traerDesc.text = ""
        binding.traerDificultad.text = ""
        binding.traerLongitud.text = ""
        binding.traerTiempo.text = ""
        binding.traerFlora.text = ""
    }
    private fun comprobarImg(Id: String){
        when(Id){
            "1" -> {
                binding.traerImgPrincipal.setImageResource(R.drawable.parque)
                binding.traerImgFlora.setImageResource(R.drawable.camping)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val pantalla = Intent(this@InfoQrSenderoActivity,
                    ConfigActivity::class.java)
                startActivity(pantalla)
                true
            }
            R.id.action_acerca_de -> {
                val pantalla = Intent(this@InfoQrSenderoActivity,
                    AcercaDeActivity::class.java)
                startActivity(pantalla)
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