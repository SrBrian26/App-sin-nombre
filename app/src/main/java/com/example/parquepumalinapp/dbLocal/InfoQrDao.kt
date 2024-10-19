package com.example.parquepumalinapp.dbLocal

import androidx.room.Dao
import androidx.room.Query

@Dao
interface InfoQrDao {

    @Query("SELECT * FROM QRSendero WHERE Id_Sendero = :id")
    suspend fun getQRSenderoById(id: String): QRSenderoEntity?

    @Query("SELECT * FROM QRCamping WHERE Id_Camp = :id")
    suspend fun getQRCampById(id: String): QRCampingEntity?

    @Query("SELECT * FROM QRHitos WHERE Id_HIto = :id")
    suspend fun getQRHitosById(id: String): QRHitosEntity?
}