package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.PlotRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.PlotRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.PlotRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.ppc.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IPlotRepository @Inject constructor(
    private val plotRoomDatasource: PlotRoomDatasource,
    private val plotRetrofitDatasource: PlotRetrofitDatasource
): PlotRepository {

    override suspend fun addAll(list: List<Plot>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = plotRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = plotRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Plot>> {
        try {
            val result = plotRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}