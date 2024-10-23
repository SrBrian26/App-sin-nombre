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
        room = (application as InfoQrApp).getDatabase() //Se obtiene la bd para el uso en la Activity
        val id_QR = intent.getStringExtra("IDqr") //Se recibe la id del qr escaneado en el menu principal
        if (id_QR != null){ //Se comprueba que el id recibido no sea nulo
            obtenerDatos(id_QR) //Se envia la id a la función
        } else {
            Toast.makeText(this@InfoQrCampingActivity, "error al escanear",
                Toast.LENGTH_SHORT).show()
        }
        val toolbar = binding.toolbarCamping
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Con ésta linea se muestra la flecha para volver en la toolbar
    }
    @SuppressLint("SetTextI18n")
    private fun obtenerDatos(Id: String) {
        limpiarCampos()
        lifecycleScope.launch{
            try{
                val infoQr = room.InfoQrDao().getQRCampById(Id)
                //se crea una variable para guardar todos los datos de la fila en la bd que coincida con el id recibido

                if (infoQr != null){ //Se comprueba que el id exista en la tabla Camping
                    // --> En las siguientes lineas se asignan los datos del sendero a los textView y la toolbar <--
                    binding.toolbarCamping.title = infoQr.Nombre_Camp
                    binding.toolbarCamping.subtitle = "Sector " + infoQr.Sector_Camp
                    binding.DescCamping.text = "Descripcion " + infoQr.Descripcion_Camp
                    binding.PrecioComunitario.text = "Precio camping Comunitario: " + infoQr.Precio_Comun_Camp
                    binding.PrecioPrivado.text = "Precio camping Privado: " + infoQr.Precio_Privado_Camp
                    binding.RestriccionesCamping.text = "Restricciones: " + infoQr.Restricciones_Camp
                    binding.CapacidadCamping.text = "Capacidad " + infoQr.Capacidad_Camp
                    binding.SuperficieCamping.text = "Superficie del Camping es: " + infoQr.Superficie_Camp
                    comprobarImg(Id) //Se envia la id a una función para saber que imagen poner por cada Sendero
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

    private fun limpiarCampos() {
        binding.DescCamping.text = ""
        binding.PrecioComunitario.text = ""
        binding.PrecioPrivado.text = ""
        binding.RestriccionesCamping.text = ""
        binding.CapacidadCamping.text = ""
        binding.SuperficieCamping.text = ""
    }

    private fun comprobarImg(Id: String){ //recibe la id y asigna imagenes
        when(Id){
            "ClagoBlanco" -> {
                binding.Clagoblancoid.setImageResource(R.drawable.clagoblanco)
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { //Realiza una acción dependiendo de la opción seleccionada
        return when (item.itemId) {
            android.R.id.home -> { //Al presionar la flecha volver envia al Menu ************
                val pantalla = Intent(this, MenuPrincipal::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}