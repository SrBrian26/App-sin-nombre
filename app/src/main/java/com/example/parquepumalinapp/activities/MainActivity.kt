package com.example.parquepumalinapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.parquepumalinapp.R
import com.example.parquepumalinapp.activities.InfoQR.InfoQrCampingActivity
import com.example.parquepumalinapp.activities.InfoQR.InfoQrHitoActivity
import com.example.parquepumalinapp.activities.InfoQR.InfoQrSenderoActivity
import com.example.parquepumalinapp.databinding.ActivityMainBinding
import com.example.parquepumalinapp.dbLocal.AppDatabase
import com.example.parquepumalinapp.dbLocal.InfoQrApp
import com.example.parquepumalinapp.navBottom.InfoParqueFragment
import com.example.parquepumalinapp.navBottom.MapaFragment
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        room = (application as InfoQrApp).getDatabase()

        binding.bottomNavigationView.setBackgroundDrawable(null)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        replaceFragment(MapaFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_mapa -> {
                    replaceFragment(MapaFragment())
                    binding.toolbar.title = "Mapa satelital"
                }
                R.id.navigation_parque -> {
                    replaceFragment(InfoParqueFragment())
                    binding.toolbar.title = "Conoce sobre nosotros"
                }
                else -> {
                    Log.d("navBottom", "error al cargar fragment")
                }
            }
            true
        }
        binding.navigationScaner.setOnClickListener {
            initScanner()
            binding.bottomNavigationView.selectedItemId = R.id.fab
        }
    }
    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Escanea un código QR")
        integrator.setTorchEnabled(false)
        integrator.setBeepEnabled(false)

        val scanIntent = integrator.createScanIntent()
        qrScanLauncher.launch(scanIntent)
    }
    private val qrScanLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val scanResult = IntentIntegrator.parseActivityResult(result.resultCode, data)
            if (scanResult != null) {
                val scannedContent = scanResult.contents
                if (!scannedContent.isNullOrEmpty()) {
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

            if (infoQrS != null || infoQrC != null || infoQrH != null) {
                val regex = Regex("([A-Za-z])(\\w+)")
                val matchResult = regex.matchEntire(Id)
                if (matchResult != null) {
                    val (codigo, fila) = matchResult.destructured
                    when (codigo) {
                        "S" -> {
                            val pantalla = Intent(this@MainActivity,
                                InfoQrSenderoActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        "C" -> {
                            val pantalla = Intent(this@MainActivity,
                                InfoQrCampingActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        "H" -> {
                            val pantalla = Intent(this@MainActivity,
                                InfoQrHitoActivity::class.java)
                            val contenedorId = Bundle()
                            contenedorId.putString("IDqr", Id)
                            pantalla.putExtras(contenedorId)
                            startActivity(pantalla)
                        }
                        else -> Toast.makeText(this@MainActivity,
                            "Codigo QR inválido", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@MainActivity, "Código QR no existe",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tool_bar, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val pantalla = Intent(this@MainActivity,
                    ConfigActivity::class.java)
                startActivity(pantalla)
                true
            }
            R.id.action_acerca_de -> {
                val pantalla = Intent(this@MainActivity,
                    AcercaDeActivity::class.java)
                startActivity(pantalla)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}