package br.com.usinasantafe.ppc.external.room.datasource.variable

import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import javax.inject.Inject

class ISampleRoomDatasource @Inject constructor(
    private val sampleDao: SampleDao
): SampleRoomDatasource {

    override suspend fun countByIdHeader(idHeader: Int): Result<Int> {
        return try {
            Result.success(sampleDao.countByIdHeader(idHeader))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun listByIdHeader(idHeader: Int): Result<List<SampleRoomModel>> {
        return try {
            Result.success(sampleDao.listByIdHeader(idHeader))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteByIdHeader(idHeader: Int): Result<Boolean> {
        return try {
            sampleDao.deleteByIdHeader(idHeader)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteById(id: Int): Result<Boolean> {
        return try {
            sampleDao.deleteById(id)
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun save(model: SampleRoomModel): Result<Boolean> {
        TODO("Not yet implemented")
    }

}