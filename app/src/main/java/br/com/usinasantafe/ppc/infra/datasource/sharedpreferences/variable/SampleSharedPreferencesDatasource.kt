package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable

import br.com.usinasantafe.ppc.utils.Field

interface SampleSharedPreferencesDatasource {
    suspend fun setValue(
        field: Field,
        value: Double
    ): Result<Boolean>
    suspend fun clean(): Result<Boolean>
}