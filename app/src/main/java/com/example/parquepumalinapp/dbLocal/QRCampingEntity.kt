package com.example.parquepumalinapp.dbLocal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QRCamping")
class QRCampingEntity (
    @PrimaryKey
    @ColumnInfo(name = "Id_Camp") var Id_Camp: String,
    @ColumnInfo(name = "Nombre_Camp") var Nombre_Camp: String,
    @ColumnInfo(name = "Descripcion_Camp") var Descripcion_Camp: String,
    @ColumnInfo(name = "Precio_Privado_Camp") var Precio_Privado_Camp: Int,
    @ColumnInfo(name = "Precio_Comun_Camp") var Precio_Comun_Camp: Int,
    @ColumnInfo(name = "Restricciones_Camp") var Restricciones_Camp: String,
    @ColumnInfo(name = "Capacidad_Camp") var Capacidad_Camp: String,
    @ColumnInfo(name = "Superficie_Camp") var Superficie_Camp: String,
    @ColumnInfo(name = "Sector_Camp") var Sector_Camp: String
)