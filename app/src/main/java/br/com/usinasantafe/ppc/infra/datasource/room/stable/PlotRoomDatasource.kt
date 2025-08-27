package br.com.usinasantafe.ppc.infra.datasource.room.stable

import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel

interface PlotRoomDatasource {
    suspend fun addAll(list: List<PlotRoomModel>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
}