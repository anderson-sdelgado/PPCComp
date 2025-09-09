package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.BASE_SHARED_PREFERENCES_TABLE_HEADER
import br.com.usinasantafe.ppc.utils.BASE_SHARED_PREFERENCES_TABLE_OS
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import com.google.gson.Gson
import java.util.Date
import javax.inject.Inject

class IHeaderSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): HeaderSharedPreferencesDatasource {

    fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_HEADER,
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

    fun get(): Result<HeaderSharedPreferencesModel> {
        try {
            val header = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_HEADER,
                null
            )
            if(header.isNullOrEmpty())
                return Result.success(
                    HeaderSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    header,
                    HeaderSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    fun save(model: HeaderSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_HEADER,
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

    override suspend fun setAuditor(
        pos: Int,
        regAuditor: Int
    ): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            when (pos) {
                1 -> {
                    clean()
                    model.regAuditor1 = regAuditor.toLong()
                }
                2 -> model.regAuditor2 = regAuditor.toLong()
                3 -> model.regAuditor3 = regAuditor.toLong()
            }
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

    override suspend fun setDate(date: Date): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.date = date
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

    override suspend fun setTurn(nroTurn: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroTurn = nroTurn
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

    override suspend fun setOS(nroOS: Int): Result<Boolean> {
        try {
            val resultGet = get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val model = resultGet.getOrNull()!!
            model.nroOS = nroOS
            val resultSave = save(model)
            if (resultSave.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSave.exceptionOrNull()!!
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

}