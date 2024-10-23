package com.example.parquepumalinapp.activities.InfoQR

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.activities.MenuPrincipal
import com.example.parquepumalinapp.databinding.ActivityInfoQrHitoBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import kotlinx.coroutines.launch

class InfoQrHitoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoQrHitoBinding
    lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoQrHitoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase() //Se obtiene la bd para el uso en la Activity
        val id_QR = intent.getStringExtra("IDqr") //Se recibe la id del qr escaneado en el menu principal
        if (id_QR != null){ //Se comprueba que el id recibido no sea nulo
            obtenerDatos(id_QR) //Se envia la id a la función
        } else {
            Toast.makeText(this@InfoQrHitoActivity, "error al escanear",
                Toast.LENGTH_SHORT).show()
        }
        val toolbar = binding.toolbarHito
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Con ésta linea se muestra la flecha para volver en la toolbar
    }
    private fun obtenerDatos(Id: String) {
        limpiarCampos()
        lifecycleScope.launch{
            try{
                val infoQr = room.InfoQrDao().getQRHitosById(Id)
                //se crea una variable para guardar todos los datos de la fila en la bd que coincida con el id recibido

                if (infoQr != null){ //Se comprueba que el id exista en la tabla Hito
                    // --> En las siguientes lineas se asignan los datos del sendero a los textView y la toolbar <--
                    binding.toolbarHito.title = infoQr.Nombre_Hito
                    binding.toolbarHito.subtitle = "Sector " + infoQr.Sector_Hito
                    binding.traerDescHito.text = infoQr.Descripcion_Hito
                    binding.traerRestricciones.text = infoQr.Restricciones_Hito
                    comprobarImg(Id) //Se envia la id a una función para saber que imagen poner por cada Sendero
                } else {
                    Toast.makeText(this@InfoQrHitoActivity, "No se encontraron " +
                            "datos para el ID proporcionado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception){
            Toast.makeText(this@InfoQrHitoActivity, "error: " +
                    e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun limpiarCampos() {
        binding.traerDescHito.text = ""
        binding.traerRestricciones.text = ""
    }

    private fun comprobarImg(Id: String){ //recibe la id y asigna imagenes
        when(Id){
            "HalerceM" -> {
                binding.traerImgPrincipalHito.setImageResource(R.drawable.hito_alerce_img_principal)
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