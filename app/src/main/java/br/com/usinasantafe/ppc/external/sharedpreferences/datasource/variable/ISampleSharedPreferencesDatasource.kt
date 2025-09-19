package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.SampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.BASE_SHARED_PREFERENCES_TABLE_SAMPLE
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import com.google.gson.Gson
import javax.inject.Inject

class ISampleSharedPreferencesDatasource @Inject constructor(
    private val sharedPreferences: SharedPreferences
): SampleSharedPreferencesDatasource {

    override suspend fun clean(): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_SAMPLE,
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

    fun save(model: SampleSharedPreferencesModel): Result<Boolean> {
        try {
            sharedPreferences.edit {
                putString(
                    BASE_SHARED_PREFERENCES_TABLE_SAMPLE,
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

    fun get(): Result<SampleSharedPreferencesModel> {
        try {
            val header = sharedPreferences.getString(
                BASE_SHARED_PREFERENCES_TABLE_SAMPLE,
                null
            )
            if(header.isNullOrEmpty())
                return Result.success(
                    SampleSharedPreferencesModel()
                )
            return Result.success(
                Gson().fromJson(
                    header,
                    SampleSharedPreferencesModel::class.java
                )
            )
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setValue(
        field: Field,
        value: Double
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
            when(field) {
                Field.TARE -> {
                    model.tare = value
                }
                Field.STALK -> model.stalk = value
                Field.WHOLE_CANE -> model.wholeCane = value
                Field.STUMP -> model.stump = value
                Field.PIECE -> model.piece = value
                Field.TIP -> model.tip = value
                Field.SLIVERS -> model.slivers = value
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

}