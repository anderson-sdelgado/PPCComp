package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.room.stable.PlotRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import javax.inject.Inject

class IPlotRoomDatasource @Inject constructor(

): PlotRoomDatasource {

    override suspend fun addAll(list: List<PlotRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}