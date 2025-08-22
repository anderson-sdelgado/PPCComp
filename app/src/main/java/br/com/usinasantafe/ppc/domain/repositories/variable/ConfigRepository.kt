package br.com.usinasantafe.ppc.domain.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config

interface ConfigRepository {
    suspend fun hasConfig(): Result<Boolean>
    suspend fun getPassword(): Result<String>
    suspend fun get(): Result<Config>
    suspend fun save(entity: Config): Result<Boolean>
    suspend fun send(entity: Config): Result<Config>
}