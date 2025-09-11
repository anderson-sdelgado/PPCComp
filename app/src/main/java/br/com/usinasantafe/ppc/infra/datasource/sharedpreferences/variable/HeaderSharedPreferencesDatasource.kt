package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable

import java.util.Date

interface HeaderSharedPreferencesDatasource {
    suspend fun setAuditor(
        pos: Int,
        regAuditor: Int
    ): Result<Boolean>
    suspend fun setDate(date: Date): Result<Boolean>
    suspend fun setTurn(nroTurn: Int): Result<Boolean>
    suspend fun setOS(nroOS: Int): Result<Boolean>
    suspend fun getOS(): Result<Int>
    suspend fun setSection(codSection: Int): Result<Boolean>
    suspend fun getSection(): Result<Int>
    suspend fun setPlot(nroPlot: Int): Result<Boolean>
}