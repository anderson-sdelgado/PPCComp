package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.sharedPreferencesModelToRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IAnalysisRepository @Inject constructor(
    private val headerRoomDatasource: HeaderRoomDatasource,
    private val headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource,
    private val sampleRoomDatasource: SampleRoomDatasource,
): AnalysisRepository {

    override suspend fun listHeader(): Result<List<Header>> {
        try {
            val resultSetClose = headerRoomDatasource.updateStatus()
            if(resultSetClose.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSetClose.exceptionOrNull()!!
                )
            }
            val resultList = headerRoomDatasource.listByStatus(Status.CLOSE)
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

    override suspend fun setOSHeader(nroOS: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setOS(nroOS)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getOSHeaderOpen(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getOS()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setSectionHeader(codSection: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setSection(codSection)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getSectionHeader(): Result<Int> {
        val result = headerSharedPreferencesDatasource.getSection()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setPlotHeader(nroPlot: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setPlot(nroPlot)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setFrontHeader(nroFront: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setFront(nroFront)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setHarvesterHeader(nroHarvester: Int): Result<Boolean> {
        val result = headerSharedPreferencesDatasource.setHarvester(nroHarvester)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result

    }

    override suspend fun setOperatorHeader(regOperator: Int): Result<Boolean> {
        try {
            val resultSet = headerSharedPreferencesDatasource.setOperator(regOperator)
            if(resultSet.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSet.exceptionOrNull()!!
                )
            }
            val resultGet = headerSharedPreferencesDatasource.get()
            if(resultGet.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            val resultSave = headerRoomDatasource.save(model.sharedPreferencesModelToRoomModel())
            if(resultSave.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
                )
            }
            return resultSave
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setStatusHeaderById(
        status: Status,
        id: Int
    ): Result<Boolean> {
        val result = headerRoomDatasource.setStatusById(status, id)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}