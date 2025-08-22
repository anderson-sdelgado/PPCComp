package br.com.usinasantafe.ppc.external.sharedpreferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.BASE_SHARE_PREFERENCES_TABLE_CONFIG
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class IConfigSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ConfigSharedPreferencesDatasource {

    override suspend fun has(): Result<Boolean> {
        try {
            val result = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            return Result.success(result != null)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun getPassword(): Result<String> {
        try {
            val result = get()
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.password!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(model: ConfigSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                    Gson().toJson(model)
                )
            }
            return Result.success(true)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun get(): Result<ConfigSharedPreferencesModel> {
        try {
            val config = sharedPreferences.getString(
                BASE_SHARE_PREFERENCES_TABLE_CONFIG,
                null
            )
            if(config.isNullOrEmpty())
                return Result.success(
                    ConfigSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    config,
                    ConfigSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}