package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences

interface HeaderSharedPreferencesDatasource {
    suspend fun setAuditor(
        pos: Int,
        regAuditor: Int
    ): Result<Boolean>
}