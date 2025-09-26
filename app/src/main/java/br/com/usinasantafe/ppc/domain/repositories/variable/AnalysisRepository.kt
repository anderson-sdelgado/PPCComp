package br.com.usinasantafe.ppc.domain.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.domain.entities.variable.Sample
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.Status
import java.util.Date

interface AnalysisRepository {
    suspend fun listHeader(): Result<List<Header>>
    suspend fun countSampleByIdHeader(idHeader: Int): Result<Int>
    suspend fun setAuditorHeader(pos: Int, regAuditor: Int): Result<Boolean>
    suspend fun setDateHeader(date: Date): Result<Boolean>
    suspend fun setTurnHeader(nroTurn: Int): Result<Boolean>
    suspend fun setOSHeader(nroOS: Int): Result<Boolean>
    suspend fun getOSHeaderOpen(): Result<Int>
    suspend fun setSectionHeader(codSection: Int): Result<Boolean>
    suspend fun getSectionHeader(): Result<Int>
    suspend fun setPlotHeader(nroPlot: Int): Result<Boolean>
    suspend fun setFrontHeader(nroFront: Int): Result<Boolean>
    suspend fun setHarvesterHeader(nroHarvester: Int): Result<Boolean>
    suspend fun setOperatorHeader(regOperator: Int): Result<Boolean>
    suspend fun setStatusHeaderById(
        status: Status,
        id: Int
    ): Result<Boolean>
    suspend fun getIdHeaderByStatus(status: Status): Result<Int>
    suspend fun deleteHeaderById(id: Int): Result<Boolean>
    suspend fun listSampleByIdHeader(idHeader: Int): Result<List<Sample>>
    suspend fun deleteSampleByIdHeader(idHeader: Int): Result<Boolean>
    suspend fun deleteSampleById(id: Int): Result<Boolean>
    suspend fun setFieldSample(
        field: Field,
        value: Double
    ): Result<Boolean>
    suspend fun getTareSample(): Result<Double>
    suspend fun setObsSample(
        stone: Boolean,
        treeStump: Boolean,
        weed: Boolean,
        anthill: Boolean
    ): Result<Boolean>
    suspend fun setSubObsSample(
        guineaGrass: Boolean,
        castorOilPlant: Boolean,
        signalGrass: Boolean,
        mucuna: Boolean,
        silkGrass: Boolean
    ): Result<Boolean>
    suspend fun saveSample(): Result<Boolean>
    suspend fun checkSend(): Result<Boolean>
}