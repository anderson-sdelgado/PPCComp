package br.com.usinasantafe.ppc.infra.datasource.room.stable

import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel

interface SectionRoomDatasource {
    suspend fun addAll(list: List<SectionRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun checkNro(nroSection: Int): Result<Boolean>
}