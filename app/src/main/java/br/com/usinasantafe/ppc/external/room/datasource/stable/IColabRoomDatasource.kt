package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import javax.inject.Inject

class IColabRoomDatasource @Inject constructor(

): ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}