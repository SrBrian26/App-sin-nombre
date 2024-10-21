package com.example.parquepumalinapp.activities.InfoQR

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.activities.MenuPrincipal
import com.example.parquepumalinapp.databinding.ActivityInfoQrCampingBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import kotlinx.coroutines.launch

class InfoQrCampingActivity : AppCompatActivity() {
    lateinit var binding: ActivityInfoQrCampingBinding
    lateinit var room: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInfoQrCampingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase()
        val id_QR = intent.getStringExtra("IDqr")
        if (id_QR != null){
            obtenerDatos(id_QR)
        } else {
            Toast.makeText(this@InfoQrCampingActivity, "error al escanear",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun obtenerDatos(Id: String) {
        lifecycleScope.launch{
            try{
                val infoQr = room.InfoQrDao().getQRCampById(Id)
                if (infoQr != null){

                } else {
                    Toast.makeText(this@InfoQrCampingActivity, "No se encontraron " +
                            "datos para el ID proporcionado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
                Toast.makeText(this@InfoQrCampingActivity, "error: " +
                        e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    //    private fun comprobarImg(Id: String){
//        when(Id){
//
//        }
//    }
    //    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.tool_bar, menu)
//        return true
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_settings -> {
//                val pantalla = Intent(this@InfoQrSenderoActivity,
//                    ConfigActivity::class.java)
//                startActivity(pantalla)
//                true
//            }
//            R.id.action_acerca_de -> {
//                val pantalla = Intent(this@InfoQrSenderoActivity,
//                    AcercaDeActivity::class.java)
//                startActivity(pantalla)
//                true
//            }
            android.R.id.home -> {
                val pantalla = Intent(this, MenuPrincipal::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}