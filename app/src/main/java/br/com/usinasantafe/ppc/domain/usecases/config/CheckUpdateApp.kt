package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.CheckNetwork
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckUpdateApp {
    suspend operator fun invoke(version: String): Result<Boolean>
}

class ICheckUpdateApp @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val configRepository: ConfigRepository
): CheckUpdateApp {

    override suspend fun invoke(version: String): Result<Boolean> {
        if (!checkNetwork()) return Result.success(false)
        val result = configRepository.checkUpdateApp(version)
        if(result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}