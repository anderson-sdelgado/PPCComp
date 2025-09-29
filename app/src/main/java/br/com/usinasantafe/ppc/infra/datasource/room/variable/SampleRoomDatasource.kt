package br.com.usinasantafe.ppc.infra.datasource.room.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel

interface SampleRoomDatasource {
    suspend fun countByIdHeader(idHeader: Int): Result<Int>
    suspend fun listByIdHeader(idHeader: Int): Result<List<SampleRoomModel>>
    suspend fun deleteByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun deleteById(id: Int): Result<Boolean>
    suspend fun save(model: SampleRoomModel): Result<Boolean>
    suspend fun setIdServById(id: Int, idServ: Int): Result<Boolean>
}