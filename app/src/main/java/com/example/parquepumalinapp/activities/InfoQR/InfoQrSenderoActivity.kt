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
import com.example.parquepumalinapp.databinding.ActivityInfoQrSenderoBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import kotlinx.coroutines.launch

class InfoQrSenderoActivity : AppCompatActivity() {

    lateinit var binding: ActivityInfoQrSenderoBinding
    lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoQrSenderoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase() //Se obtiene la bd para el uso en la Activity
        val id_QR = intent.getStringExtra("IDqr") //Se recibe la id del qr escaneado en el menu principal
        if (id_QR != null){ //Se comprueba que el id recibido no sea nulo
            obtenerDatos(id_QR) //Se envia la id a la función
        } else {
            Toast.makeText(this@InfoQrSenderoActivity, "error al escanear",
                Toast.LENGTH_SHORT).show()
        }
        val toolbar = binding.toolbar2
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Con ésta linea se muestra la flecha para volver en la toolbar
    }

    @SuppressLint("SetTextI18n")
    private fun obtenerDatos(Id: String){ //Función que recibe el id para encontrar los datos en la bd y traerlos a los textView
        limpiarCampos()
        lifecycleScope.launch {
            try {
                val infoQr = room.InfoQrDao().getQRSenderoById(Id)
                //se crea una variable para guardar todos los datos de la fila en la bd que coincida con el id recibido

                if (infoQr != null) { //Se comprueba que el id exista en la tabla Senderos
                    // --> En las siguientes lineas se asignan los datos del sendero a los textView y la toolbar <--
                    binding.toolbar2.title = infoQr.Nombre_Sendero
                    binding.toolbar2.subtitle = "Sector " + infoQr.Sector_Sendero
                    binding.traerDesc.text = infoQr.Descripcion_Sendero
                    binding.traerDificultad.text = "Dificultad: " + infoQr.Dificultad_Sendero
                    binding.traerLongitud.text =  "Tiene una longitud de " + infoQr.Longitud_Sendero
                    binding.traerFlora.text = infoQr.Flora_Sendero
                    binding.traerRestricciones.text = "Restricciones:\n\n" + infoQr.Restricciones_Senderos
                    binding.traerTiempo.text = "El sendero dura " + infoQr.Tiempo_Sendero
                    comprobarImg(Id) //Se envia la id a una función para saber que imagen poner por cada Sendero
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
    private fun comprobarImg(Id: String){ //recibe la id y asigna imagenes
        when(Id){
            "Salerce" -> {
                binding.traerImgPrincipal.setImageResource(R.drawable.img_sendero_alere)
                binding.traerImgFlora.setImageResource(R.drawable.img_flora_alerce)
            }
        }
    }
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //Trae el menu de los 3 puntitos
//        menuInflater.inflate(R.menu.tool_bar, menu)
//        return true
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { //Realiza una acción dependiendo de la opción seleccionada
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
            android.R.id.home -> { //Al presionar la flecha volver envia al Menu ************
                val pantalla = Intent(this, MenuPrincipal::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}