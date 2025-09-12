package br.com.usinasantafe.ppc.external.room.datasource.variable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IHeaderRoomDatasource @Inject constructor(
    private val headerDao: HeaderDao
): HeaderRoomDatasource {

    override suspend fun listByStatus(status: Status): Result<List<HeaderRoomModel>> {
        try {
            val list = headerDao.listByStatus(status)
            return Result.success(list)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: HeaderRoomModel): Result<Boolean> {
        try {
            headerDao.insert(model)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}