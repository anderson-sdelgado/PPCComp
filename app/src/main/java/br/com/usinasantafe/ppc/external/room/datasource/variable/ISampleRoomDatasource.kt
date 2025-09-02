package br.com.usinasantafe.ppc.external.room.datasource.variable

import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
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

}