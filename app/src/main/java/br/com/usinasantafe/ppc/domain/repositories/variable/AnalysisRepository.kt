package br.com.usinasantafe.ppc.domain.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Header

interface AnalysisRepository {
    suspend fun listHeaderByStatusOpen(): Result<List<Header>>
    suspend fun countSampleByIdHeader(idHeader: Int): Result<Int>
    suspend fun setAuditor(pos: Int, regAuditor: Int): Result<Boolean>
}