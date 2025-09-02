package br.com.usinasantafe.ppc.infra.datasource.room.variable

interface SampleRoomDatasource {
    suspend fun countByIdHeader(idHeader: Int): Result<Int>
}