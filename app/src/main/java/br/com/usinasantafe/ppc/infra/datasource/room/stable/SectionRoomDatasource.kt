package br.com.usinasantafe.ppc.infra.datasource.room.stable

import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel

interface SectionRoomDatasource {
    suspend fun addAll(list: List<SectionRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun checkCod(codSection: Int): Result<Boolean>
    suspend fun getIdByCod(codSection: Int): Result<Int>
}