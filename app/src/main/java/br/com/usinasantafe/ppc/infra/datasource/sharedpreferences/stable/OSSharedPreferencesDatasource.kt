package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable

import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel

interface OSSharedPreferencesDatasource {
    suspend fun clean(): Result<Boolean>
    suspend fun save(model: OSSharedPreferencesModel): Result<Boolean>
}