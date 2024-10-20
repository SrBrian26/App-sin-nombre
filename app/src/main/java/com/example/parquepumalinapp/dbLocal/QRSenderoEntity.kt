package com.example.parquepumalinapp.dbLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QRSendero")
data class QRSenderoEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id_Sendero") var Id_Sendero: String,
    @ColumnInfo(name = "Nombre_Sendero") var Nombre_Sendero: String,
    @ColumnInfo(name = "Descripcion_Sendero") var Descripcion_Sendero: String,
    @ColumnInfo(name = "Dificultad_Sendero") var Dificultad_Sendero: String,
    @ColumnInfo(name = "Longitud_Sendero") var Longitud_Sendero: String,
    @ColumnInfo(name = "Tiempo_Sendero") var Tiempo_Sendero: String,
    @ColumnInfo(name = "Restricciones_Senderos") var Restricciones_Senderos: String,
    @ColumnInfo(name = "Sector_Sendero") var Sector_Sendero: String,
    @ColumnInfo(name = "Flora_Sendero") var Flora_Sendero: String
)