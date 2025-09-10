package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.stable

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable.OSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.BASE_SHARED_PREFERENCES_TABLE_OS
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class IOSSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): OSSharedPreferencesDatasource {

    fun get(): Result<OSSharedPreferencesModel?> {
        try {
            val model = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_OS,
                null
            )
            if(model.isNullOrEmpty())
                return Result.success(
                    null
                )
            return Result.success(
                Gson().fromJson(
                    model,
                    OSSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_OS,
                    null
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

    override suspend fun save(model: OSSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_OS,
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

    override suspend fun checkHas(): Result<Boolean> {
        try {
            val result = get()
            if (result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val model = result.getOrNull()
            val check = model != null
            return Result.success(check)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkNroAndIdSection(
        nroOS: Int,
        idSection: Int
    ): Result<Boolean> {
        try {
            val result = get()
            if (result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val model = result.getOrNull()!!
            val check = model.nroOS == nroOS && model.idSection == idSection
            return Result.success(check)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }

    }

}