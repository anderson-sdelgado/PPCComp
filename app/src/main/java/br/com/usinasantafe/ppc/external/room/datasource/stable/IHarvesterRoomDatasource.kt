package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.infra.datasource.room.stable.HarvesterRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IHarvesterRoomDatasource @Inject constructor(
    private val harvesterDao: HarvesterDao
): HarvesterRoomDatasource {

    override suspend fun addAll(list: List<HarvesterRoomModel>): Result<Boolean> {
        try {
            harvesterDao.insertAll(list)
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
            harvesterDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkNroHarvester(nroHarvester: Int): Result<Boolean> {
        try {
            val result = harvesterDao.checkNroHarvester(nroHarvester) > 0
            return Result.success(result)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }

    }
}