package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckHarvester {
    suspend operator fun invoke(
        nroHarvester: String
    ): Result<Boolean>
}

class ICheckHarvester @Inject constructor(
    private val harvesterRepository: HarvesterRepository
): CheckHarvester {

    override suspend fun invoke(
        nroHarvester: String
    ): Result<Boolean> {
        try {
            val result = harvesterRepository.checkNroHarvester(nroHarvester.toInt())
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