package br.com.usinasantafe.ppc.domain.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header
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
}