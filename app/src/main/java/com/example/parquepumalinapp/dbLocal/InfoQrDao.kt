package com.example.parquepumalinapp.dbLocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InfoQrDao {

    @Query("SELECT * FROM InfoQR")
    suspend fun getAllQr(): List<InfoQrEntity>

    @Query("SELECT * FROM InfoQR WHERE id = :id")
    suspend fun getInfoQrById(id: String): InfoQrEntity?

    @Insert
    suspend fun insertQR(infoQrEntity: InfoQrEntity)
}