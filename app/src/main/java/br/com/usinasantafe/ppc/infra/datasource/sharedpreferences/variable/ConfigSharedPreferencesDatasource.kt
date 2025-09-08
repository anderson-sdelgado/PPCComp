package br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable

import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.FlagUpdate

interface ConfigSharedPreferencesDatasource {
    suspend fun has(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean>
    suspend fun get(): Result<ConfigSharedPreferencesModel>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
}