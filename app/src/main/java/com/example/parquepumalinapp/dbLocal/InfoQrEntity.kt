package com.example.parquepumalinapp.dbLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "InfoQR")
data class InfoQrEntity(
    @PrimaryKey
    @ColumnInfo(name = "Id") var Id: String,
    @ColumnInfo(name = "NombreZona") var NombreZona: String,
    @ColumnInfo(name = "Descripcion") var Descripcion: String,
    @ColumnInfo(name = "Ubicacion") var Ubicacion: String,
    @ColumnInfo(name = "Sendero") var Sendero: String,
    @ColumnInfo(name = "tiempo") var tiempo: Int,
    @ColumnInfo(name = "infoFauna") var infoFauna: String
)