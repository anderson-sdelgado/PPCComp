package br.com.usinasantafe.ppc.domain.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.utils.FlagUpdate

interface ConfigRepository {
    suspend fun hasConfig(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun get(): Result<Config>
    suspend fun save(entity: Config): Result<Boolean>
    suspend fun send(entity: Config): Result<Config>
    suspend fun setFlagUpdate(flagUpdate: FlagUpdate): Result<Boolean>
    suspend fun getFlagUpdate(): Result<FlagUpdate>
}