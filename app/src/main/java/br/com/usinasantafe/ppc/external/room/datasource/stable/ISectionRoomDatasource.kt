package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.infra.datasource.room.stable.SectionRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class ISectionRoomDatasource @Inject constructor(
    private val sectionDao: SectionDao
): SectionRoomDatasource {

    override suspend fun addAll(list: List<SectionRoomModel>): Result<Boolean> {
        try {
            sectionDao.insertAll(list)
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
            sectionDao.deleteAll()
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkCod(codSection: Int): Result<Boolean> {
        try {
            val check = sectionDao.count(codSection) > 0
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getIdByCod(codSection: Int): Result<Int> {
        try {
            val id = sectionDao.getIdByCod(codSection)
            return Result.success(id)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}