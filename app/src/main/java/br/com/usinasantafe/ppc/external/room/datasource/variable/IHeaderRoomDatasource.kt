package br.com.usinasantafe.ppc.external.room.datasource.variable

import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import javax.inject.Inject

class IHeaderRoomDatasource @Inject constructor(

): HeaderRoomDatasource {

    override suspend fun listByStatus(status: Status): Result<List<HeaderRoomModel>> {
        TODO("Not yet implemented")
    }

}