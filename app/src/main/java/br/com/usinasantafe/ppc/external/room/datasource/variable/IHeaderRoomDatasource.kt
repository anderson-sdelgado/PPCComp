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

    override suspend fun updateStatus(): Result<Boolean> {
        try {
            headerDao.updateStatus()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

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

    override suspend fun setStatusById(
        status: Status,
        id: Int
    ): Result<Boolean> {
        try {
            headerDao.updateStatusById(status, id)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdByStatus(status: Status): Result<Int> {
        try {
            val id = headerDao.getIdByStatus(status)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteById(id: Int): Result<Boolean> {
        try {
            headerDao.deleteById(id)
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}