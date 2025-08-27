package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.HarvesterRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.HarvesterRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.ppc.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IHarvesterRepository @Inject constructor(
    private val harvesterRoomDatasource: HarvesterRoomDatasource,
    private val harvesterRetrofitDatasource: HarvesterRetrofitDatasource
): HarvesterRepository {

    override suspend fun addAll(list: List<Harvester>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = harvesterRoomDatasource.addAll(roomModelList)
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
        val result = harvesterRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Harvester>> {
        try {
            val result = harvesterRetrofitDatasource.listAll(token)
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