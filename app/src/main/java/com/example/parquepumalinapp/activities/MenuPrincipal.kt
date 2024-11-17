package com.example.parquepumalinapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.activities.InfoQR.InfoQrCampingActivity
import com.example.parquepumalinapp.activities.InfoQR.InfoQrHitoActivity
import com.example.parquepumalinapp.activities.InfoQR.InfoQrSenderoActivity
import com.example.parquepumalinapp.databinding.ActivityMenuPrincipalBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import com.example.parquepumalinapp.navBottom.InfoParque.InfoParqueFragment
import com.example.parquepumalinapp.navBottom.MapaFragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch

class MenuPrincipal : AppCompatActivity() {

    private lateinit var binding: ActivityMenuPrincipalBinding
    private lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase() //Se obtiene la bd para el uso en la Activity

        binding.bottomNavigationView.setBackgroundDrawable(null) //Oculta el fondo del BottomNavigationView
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        replaceFragment(InfoParqueFragment()) //Inicializa la App con el fragment de bienvenida
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_mapa -> { //al presionar el boton del bottomNavigationView cambia al fragment del mapa
                    replaceFragment(MapaFragment())
                    binding.toolbar.title = "Mapa"
                }
                R.id.navigation_parque -> { //al presionar el boton del bottomNavigationView cambia al fragment de la info Paque
                    replaceFragment(InfoParqueFragment())
                    binding.toolbar.title = "Conoce sobre nosotros"
                }
                else -> {
                    Log.d("navBottom", "error al cargar fragment")
                }
            }
            true
        }
        binding.navigationScaner.setOnClickListener { //Llama a la función initScanner cuando se presiona el boton del escaner
            initScanner()
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Muestra un diálogo de confirmación antes de salir
                AlertDialog.Builder(this@MenuPrincipal).apply {
                    setMessage("¿Desea salir de la aplicación?")
                    setPositiveButton("Sí") {_,_->
                        finishAffinity() // Finaliza todas las actividades y cierra la app
                    }
                    setNegativeButton("No", null) // Cierra el diálogo
                    create().show()
                }
            }
        })
    }
    private fun initScanner() { //Función para activar un lector de códigos QR
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) //Designa que solo se pueden escanear códigos QR
        integrator.setPrompt("Escanea un código QR")
        integrator.setTorchEnabled(false) //Desactiva la linterna al escanear
        integrator.setBeepEnabled(false) //Desactiva el sonido al escanear

        val scanIntent = integrator.createScanIntent()
        qrScanLauncher.launch(scanIntent)
    }
    private val qrScanLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) { //Verifica que el escaneo se completó correctamente
            val data = result.data
            val scanResult = IntentIntegrator.parseActivityResult(result.resultCode, data) //Obtiene el resultado del escaneo
            if (scanResult != null) { //Comprueba que el resultado sea valido
                val scannedContent = scanResult.contents //Obitiene el contenido del resultado (texto)
                if (!scannedContent.isNullOrEmpty()) { //Si no está vacio manda la id a la función comprobarID
                    comprobarID(scannedContent)
                } else {
                    Toast.makeText(this, "Código QR inválido",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Escaneo cancelado",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun comprobarID(Id: String) {
        lifecycleScope.launch {
            val infoQrS = room.InfoQrDao().getQRSenderoById(Id)
            val infoQrC = room.InfoQrDao().getQRCampById(Id)
            val infoQrH = room.InfoQrDao().getQRHitosById(Id)
            //se crean variables para guardar todos los datos de la fila en la bd que coincida con el id recibido

            if (infoQrS != null || infoQrC != null || infoQrH != null) { //Se comprueba que el id exista en alguna de las tablas
                val regex = Regex("([A-Za-z])(\\w+)") //formato para separar la primera letra del id y el resto en dos
                val matchResult = regex.matchEntire(Id) //separa el id en dos con el formato anterior
                if (matchResult != null) { //comprueba que es valido
                    val (codigo, fila) = matchResult.destructured //separa el id en dos valores diferentes
                    when (codigo) {
                        //Cuando el código (la primera letra) sea S, C o H direcciona a las Activities correspondientes y manda el id del QR
                        "S" -> {
                            val pantalla = Intent(this@MenuPrincipal,
                                InfoQrSenderoActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        "C" -> {
                            val pantalla = Intent(this@MenuPrincipal,
                                InfoQrCampingActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        "H" -> {
                            val pantalla = Intent(this@MenuPrincipal,
                                InfoQrHitoActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        else -> Toast.makeText(this@MenuPrincipal,
                            "Codigo QR inválido", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@MenuPrincipal, "Código QR no existe",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){ //Función para reemplazar fragmentos
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean { //Trae el menu de los 3 puntitos
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean { //Realiza una acción dependiendo de la opción seleccionada
        return when (item.itemId) {
            R.id.action_settings -> {
                val pantalla = Intent(this@MenuPrincipal,
                    ConfigActivity::class.java)
                startActivity(pantalla)
                true
            }
            R.id.action_acerca_de -> {
                val pantalla = Intent(this@MenuPrincipal,
                    AcercaDeActivity::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}