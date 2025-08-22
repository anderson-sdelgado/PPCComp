package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SendDataConfig {
    suspend operator fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Config>
}

class ISendDataConfig @Inject constructor(
    private val configRepository: ConfigRepository
): SendDataConfig {

    override suspend fun invoke(
        number: String,
        password: String,
        version: String
    ): Result<Config> {
        try {
            val entity = Config(
                number = number.toLong(),
                password = password,
                version = version
            )
            val result = configRepository.send(entity)
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

}