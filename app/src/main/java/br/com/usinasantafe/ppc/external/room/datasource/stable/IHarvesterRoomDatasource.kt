package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.room.stable.HarvesterRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import javax.inject.Inject

class IHarvesterRoomDatasource @Inject constructor(

): HarvesterRoomDatasource {
    override suspend fun addAll(list: List<HarvesterRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}