package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable.OSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.toSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IOSRepository @Inject constructor(
    private val osSharedPreferencesDatasource: OSSharedPreferencesDatasource,
    private val osRetrofitDatasource: OSRetrofitDatasource,
): OSRepository {

    override suspend fun deleteAll(): Result<Boolean> {
        val result = osSharedPreferencesDatasource.clean()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getByNroOS(
        token: String,
        nroOS: Int
    ): Result<OS> {
        try {
            val result = osRetrofitDatasource.getByNroOS(
                token = token,
                nroOS = nroOS
            )
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entity = result.getOrNull()!!.retrofitModelToEntity()
            return Result.success(entity)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun add(entity: OS): Result<Boolean> {
        try {
            val result = osSharedPreferencesDatasource.save(entity.toSharedPreferencesModel())
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkSectionAndOS(
        idSection: Int,
        nroOS: Int
    ): Result<Boolean> {
        try {
            val resultCheckHas = osSharedPreferencesDatasource.checkHas()
            if (resultCheckHas.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckHas.exceptionOrNull()!!
                )
            }
            val check = resultCheckHas.getOrNull()!!
            if (!check) return Result.success(true)
            val resultCheckNroAndIdSection = osSharedPreferencesDatasource.checkNroAndIdSection(
                nroOS = nroOS,
                idSection = idSection
            )
            if (resultCheckNroAndIdSection.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckNroAndIdSection.exceptionOrNull()!!
                )
            }
            return resultCheckNroAndIdSection
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}