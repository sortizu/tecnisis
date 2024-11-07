package com.example.tecnisis.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

class SolicitudRepository (private val solicitudDao: SolicitudDao, private val obraDao: ObraDao) {
    suspend fun getSolicitudesWithObras(): Flow<Map<Solicitud, Obra>> {
        return solicitudDao.getAll().map { solicitudes ->
            val obras = obraDao.getAll().first()
            val obrasMap = obras.associateBy { it.idObra }

            solicitudes.associateWith { solicitud ->
                obrasMap[solicitud.idObra]!!
            }
        }
    }
}