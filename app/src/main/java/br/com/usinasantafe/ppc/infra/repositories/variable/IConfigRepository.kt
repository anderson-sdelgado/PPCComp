package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.entityToRetrofitModel
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.retrofitToEntity
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.entityToSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.sharedPreferencesModelToEntity
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.net.SocketTimeoutException
import javax.inject.Inject

class IConfigRepository @Inject constructor(
    private val configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource,
    private val configRetrofitDatasource: ConfigRetrofitDatasource
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
        try {
            val result = configRetrofitDatasource.recoverToken(
                entity.entityToRetrofitModel()
            )
            if(result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val configRetrofitModel = result.getOrNull()!!
            val entity = configRetrofitModel.retrofitToEntity()
            return Result.success(entity)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean> {
        val result = configSharedPreferencesDatasource.setFlagUpdate(flagUpdate)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getFlagUpdate(): Result<FlagUpdate> {
        val result = configSharedPreferencesDatasource.getFlagUpdate()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun checkUpdateApp(version: String): Result<Boolean> {
        try {
            val resultGet = configSharedPreferencesDatasource.get()
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val sharedPreferencesModel = resultGet.getOrNull()!!
            val entityOutput = sharedPreferencesModel.sharedPreferencesModelToEntity()
            if(entityOutput.number == null) return Result.success(false)
            val resultRecover = configRetrofitDatasource.recoverToken(
                entityOutput.entityToRetrofitModel()
            )
            if(resultRecover.isFailure) {
                val error = resultRecover.exceptionOrNull()!!
                if(error.cause is SocketTimeoutException) {
                    return Result.success(false)
                }
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultRecover.exceptionOrNull()!!
                )
            }
            val configRetrofitModel = resultRecover.getOrNull()!!
            val entityInput = configRetrofitModel.retrofitToEntity()
            val resultSet = configSharedPreferencesDatasource.setVersionUpdate(entityInput.versionUpdate!!)
            if (resultSet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSet.exceptionOrNull()!!
                )
            }
            val check = entityInput.versionUpdate != version
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun updateApp(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}