package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckAccessInitial {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckAccessInitial @Inject constructor(
    private val configRepository: ConfigRepository
): CheckAccessInitial {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultCheckHasConfig = configRepository.hasConfig()
            if (resultCheckHasConfig.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckHasConfig.exceptionOrNull()!!
                )
            }
            val hasConfig = resultCheckHasConfig.getOrNull()!!
            if (!hasConfig)
                return Result.success(false)
            val resultGetFlagUpdate = configRepository.getFlagUpdate()
            if (resultGetFlagUpdate.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetFlagUpdate.exceptionOrNull()!!
                )
            }
            val flagUpdate = resultGetFlagUpdate.getOrNull()!!
            val check = flagUpdate == FlagUpdate.UPDATED
            return Result.success(check)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}