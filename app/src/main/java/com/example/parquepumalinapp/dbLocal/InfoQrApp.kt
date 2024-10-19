package com.example.parquepumalinapp.dbLocal

import android.app.Application
import android.util.Log
import androidx.room.Room
import java.io.FileOutputStream

class InfoQrApp : Application() {
    lateinit var room: AppDatabase

    override fun onCreate() {
        super.onCreate()

        copyDatabase()

        room = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "pruebaa.db"
        ).createFromAsset("pruebaa.db")
            .build()
    }
    private fun copyDatabase() {
        val dbFile = getDatabasePath("pruebaa.db")

        if (!dbFile.exists()) {
            assets.open("pruebaa.db").use { inputStream ->
                FileOutputStream(dbFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            Log.d("InfoQrApp", "Base de datos copiada correctamente")
        } else {
            Log.d("InfoQrApp", "La base de datos ya existe en la carpeta interna")
        }
    }
    fun getDatabase(): AppDatabase {
        return room
    }
}