package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.entityToSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.sharedPreferencesModelToEntity
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.text.get

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource
): ConfigRepository {

    override suspend fun hasConfig(): Result<Boolean> {
        val result = configSharedPreferencesDatasource.has()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun getPassword(): Result<String> {
        val result = configSharedPreferencesDatasource.getPassword()
        if (result.isFailure)
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        return result
    }

    override suspend fun get(): Result<Config> {
        try {
            val result = configSharedPreferencesDatasource.get()
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val config = result.getOrNull()!!
            return Result.success(config.sharedPreferencesModelToEntity())
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun save(entity: Config): Result<Boolean> {
        try {
            val sharedPreferencesModel = entity.entityToSharedPreferencesModel()
            val result = configSharedPreferencesDatasource.save(sharedPreferencesModel)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun send(entity: Config): Result<Config> {
        TODO("Not yet implemented")
    }

}