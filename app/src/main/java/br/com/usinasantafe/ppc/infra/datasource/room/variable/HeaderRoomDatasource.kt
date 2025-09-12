package br.com.usinasantafe.ppc.infra.datasource.room.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status

interface HeaderRoomDatasource {
    suspend fun listByStatus(status: Status): Result<List<HeaderRoomModel>>
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
}