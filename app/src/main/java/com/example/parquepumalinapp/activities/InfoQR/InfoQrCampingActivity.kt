package com.example.parquepumalinapp.activities.InfoQR

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.R
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
        val toolbar = binding.toolbarCamping
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    @SuppressLint("SetTextI18n")
    private fun obtenerDatos(Id: String) {
        lifecycleScope.launch{
            try{
                val infoQr = room.InfoQrDao().getQRCampById(Id)
                if (infoQr != null){
                    binding.toolbarCamping.title = infoQr.Nombre_Camp
                    binding.toolbarCamping.subtitle = "Sector " + infoQr.Sector_Camp
                    binding.DescCamping.text = infoQr.Descripcion_Camp
                    binding.PrecioComunitario.text = "Precio Comunitario: $" + infoQr.Precio_Comun_Camp
                    binding.PrecioPrivado.text = "Precio Privado: $" + infoQr.Precio_Privado_Camp
                    binding.RestriccionesCamping.text = "Restricciones:\n\n" + infoQr.Restricciones_Camp
                    binding.CapacidadCamping.text = "Capacidad: " + infoQr.Capacidad_Camp
                    binding.SuperficieCamping.text = "Superficie del Camping: " + infoQr.Superficie_Camp
                    comprobarImg(Id)
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
        private fun comprobarImg(Id: String){
        when(Id){
            "ClagoBlanco" -> {
                binding.Clagoblancoid.setImageResource(R.drawable.img_camping_lago_blanco)

            }
        }
    }
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