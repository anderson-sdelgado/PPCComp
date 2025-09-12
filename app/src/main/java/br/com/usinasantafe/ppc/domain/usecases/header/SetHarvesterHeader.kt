package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetHarvesterHeader {
    suspend operator fun invoke(
        nroHarvester: String
    ): Result<Boolean>
}

class ISetHarvesterHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetHarvesterHeader {

    override suspend fun invoke(
        nroHarvester: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setHarvesterHeader(nroHarvester.toInt())
            if(result.isFailure){
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