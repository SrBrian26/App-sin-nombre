package com.example.parquepumalinapp.dbLocal

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QRSenderoEntity::class, QRCampingEntity::class, QRHitosEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun InfoQrDao() : InfoQrDao
}