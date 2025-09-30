package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.domain.entities.variable.Sample
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.AnalysisRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.SampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.headerRoomModelToRetrofitModel
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.sampleRoomModelToRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.variable.roomModelToEntity
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.sharedPreferencesModelToRoomModel
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

class IAnalysisRepository @Inject constructor(
    private val headerRoomDatasource: HeaderRoomDatasource,
    private val headerSharedPreferencesDatasource: HeaderSharedPreferencesDatasource,
    private val sampleRoomDatasource: SampleRoomDatasource,
    private val sampleSharedPreferencesDatasource: SampleSharedPreferencesDatasource,
    private val analysisRetrofitDatasource: AnalysisRetrofitDatasource
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

    override suspend fun getIdHeaderByStatus(status: Status): Result<Int> {
        val result = headerRoomDatasource.getIdByStatus(status)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun deleteHeaderById(id: Int): Result<Boolean> {
        val result = headerRoomDatasource.deleteById(id)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listSampleByIdHeader(idHeader: Int): Result<List<Sample>> {
        try {
            val result = sampleRoomDatasource.listByIdHeader(idHeader)
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val modelList = result.getOrNull()!!
            val entityList = modelList.map { it.roomModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteSampleByIdHeader(idHeader: Int): Result<Boolean> {
        val result = sampleRoomDatasource.deleteByIdHeader(idHeader)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result

    }

    override suspend fun deleteSampleById(id: Int): Result<Boolean> {
        val result = sampleRoomDatasource.deleteById(id)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setFieldSample(
        field: Field,
        value: Double
    ): Result<Boolean> {
        try {
            if(field == Field.TARE){
                val resultClean = sampleSharedPreferencesDatasource.clean()
                if(resultClean.isFailure){
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultClean.exceptionOrNull()!!
                    )
                }
            }
            val result = sampleSharedPreferencesDatasource.setValue(field, value)
            if(result.isFailure){
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

    override suspend fun getTareSample(): Result<Double> {
        val result = sampleSharedPreferencesDatasource.getTare()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setObsSample(
        stone: Boolean,
        treeStump: Boolean,
        weed: Boolean,
        anthill: Boolean
    ): Result<Boolean> {
        val result = sampleSharedPreferencesDatasource.setObs(
            stone = stone,
            treeStump = treeStump,
            weed = weed,
            anthill = anthill
        )
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun setSubObsSample(
        guineaGrass: Boolean,
        castorOilPlant: Boolean,
        signalGrass: Boolean,
        mucuna: Boolean,
        silkGrass: Boolean
    ): Result<Boolean> {
        val result = sampleSharedPreferencesDatasource.setSubObs(
            guineaGrass = guineaGrass,
            castorOilPlant = castorOilPlant,
            signalGrass = signalGrass,
            mucuna = mucuna,
            silkGrass = silkGrass
        )
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun saveSample(): Result<Boolean> {
        try {
            val resultGet = sampleSharedPreferencesDatasource.get()
            if(resultGet.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val sampleSharedPreferencesModel = resultGet.getOrNull()!!
            val resultGetId = headerRoomDatasource.getIdByStatus(Status.OPEN)
            if(resultGetId.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val model = sampleSharedPreferencesModel.sharedPreferencesModelToRoomModel(idHeader)
            val result = sampleRoomDatasource.save(model)
            if(result.isFailure){
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

    override suspend fun checkSend(): Result<Boolean> {
        val result = headerRoomDatasource.checkSend()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun send(
        token: String,
        number: Long
    ): Result<Boolean> {
        try {
            val resultListSend = headerRoomDatasource.listByStatusSend(StatusSend.SEND)
            if(resultListSend.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultListSend.exceptionOrNull()!!
                )
            }
            val headerRoomModelList = resultListSend.getOrNull()!!
            val retrofitModelOutputList = headerRoomModelList.map { headerRoomModel ->
                val resultSampleList = sampleRoomDatasource.listByIdHeader(headerRoomModel.id!!)
                if(resultSampleList.isFailure){
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultSampleList.exceptionOrNull()!!
                    )
                }
                val sampleRoomModelList = resultSampleList.getOrNull()!!
                return@map headerRoomModel.headerRoomModelToRetrofitModel(
                    number = number,
                    sampleList = sampleRoomModelList.map { it.sampleRoomModelToRetrofitModel() }
                )
            }
            val result = analysisRetrofitDatasource.send(
                token = token,
                retrofitModelOutputList = retrofitModelOutputList
            )
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val headerRetrofitModelList = result.getOrNull()!!
            for(headerRetrofitModel in headerRetrofitModelList){
                for(sampleRetrofitModel in headerRetrofitModel.sampleList){
                    val resultSetIdServ = sampleRoomDatasource.setIdServById(
                        id = sampleRetrofitModel.id,
                        idServ = sampleRetrofitModel.idServ
                    )
                    if(resultSetIdServ.isFailure){
                        return resultFailure(
                            context = getClassAndMethod(),
                            cause = resultSetIdServ.exceptionOrNull()!!
                        )
                    }
                }
                val resultSetIdServer = headerRoomDatasource.setIdServAndSentById(
                    id = headerRetrofitModel.id,
                    idServ = headerRetrofitModel.idServ
                )
                if(resultSetIdServer.isFailure){
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultSetIdServer.exceptionOrNull()!!
                    )
                }
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }


}