package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetFinishUpdateAllTable {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetFinishUpdateAllTable @Inject constructor(
    private val configRepository: ConfigRepository
): SetFinishUpdateAllTable {

    override suspend fun invoke(): Result<Boolean> {
        val result = configRepository.setFlagUpdate(FlagUpdate.UPDATED)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}