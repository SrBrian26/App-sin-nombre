package com.example.parquepumalinapp.dbLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QRHitos")
data class QRHitosEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id_Hito") var Id_Hito: String,
    @ColumnInfo(name = "Nombre_Hito") var Nombre_Hito: String,
    @ColumnInfo(name = "Descripcion_Hito") var Descripcion_Hito: String,
    @ColumnInfo(name = "Sector_Hito") var Sector_Hito: String
)