package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IAnalysisRepository @Inject constructor(
    private val headerRoomDatasource: HeaderRoomDatasource,
    private val headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource,
    private val sampleRoomDatasource: SampleRoomDatasource,
): AnalysisRepository {

    override suspend fun listHeaderByStatusOpen(): Result<List<Header>> {
        try {
            val resultList = headerRoomDatasource.listByStatus(Status.OPEN)
            if(resultList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            val modelList = resultList.getOrNull()!!
            val entityList = modelList.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun countSampleByIdHeader(idHeader: Int): Result<Int> {
        val result = sampleRoomDatasource.countByIdHeader(idHeader)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setAuditorHeader(
        pos: Int,
        regAuditor: Int
    ): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setAuditor(pos, regAuditor)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setDateHeader(date: Date): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setDate(date)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setTurnHeader(nroTurn: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setTurn(nroTurn)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}