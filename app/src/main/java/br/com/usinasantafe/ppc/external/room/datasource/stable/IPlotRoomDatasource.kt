package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.infra.datasource.room.stable.PlotRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IPlotRoomDatasource @Inject constructor(
    private val plotDao: PlotDao
): PlotRoomDatasource {

    override suspend fun addAll(list: List<PlotRoomModel>): Result<Boolean> {
        try {
            plotDao.insertAll(list)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        try {
            plotDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkByNroPlotAndIdSection(
        nroPlot: Int,
        idSection: Int
    ): Result<Boolean> {
        try {
            val qtd = plotDao.checkByNroPlotAndIdSection(
                nroPlot = nroPlot,
                idSection = idSection
            )
            return Result.success(qtd > 0)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}