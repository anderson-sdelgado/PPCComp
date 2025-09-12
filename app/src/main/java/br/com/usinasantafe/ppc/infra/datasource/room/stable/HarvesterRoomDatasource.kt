package br.com.usinasantafe.ppc.infra.datasource.room.stable

import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel

interface HarvesterRoomDatasource {
    suspend fun addAll(list: List<HarvesterRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun checkNroHarvester(nroHarvester: Int): Result<Boolean>

}