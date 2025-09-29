package br.com.usinasantafe.ppc.infra.datasource.room.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend

interface HeaderRoomDatasource {
    suspend fun updateStatus(): Result<Boolean>
    suspend fun listByStatus(status: Status): Result<List<HeaderRoomModel>>
    suspend fun save(model: HeaderRoomModel): Result<Boolean>
    suspend fun setStatusById(status: Status, id: Int): Result<Boolean>
    suspend fun getIdByStatus(status: Status): Result<Int>
    suspend fun deleteById(id: Int): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
    suspend fun listByStatusSend(statusSend: StatusSend): Result<List<HeaderRoomModel>>
    suspend fun setIdServAndSentById(id: Int, idServ: Int): Result<Boolean>
}