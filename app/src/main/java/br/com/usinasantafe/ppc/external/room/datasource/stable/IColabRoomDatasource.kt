package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IColabRoomDatasource @Inject constructor(
    private val colabDao: ColabDao
): ColabRoomDatasource {

    override suspend fun addAll(list: List<ColabRoomModel>): Result<Boolean> {
        try {
            colabDao.insertAll(list)
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
            colabDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun check(regColab: Int): Result<Boolean> {
        try {
            val check = colabDao.count(regColab) > 0
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}