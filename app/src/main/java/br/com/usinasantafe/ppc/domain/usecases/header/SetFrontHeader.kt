package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetFrontHeader {
    suspend operator fun invoke(
        nroFront: String
    ): Result<Boolean>
}

class ISetFrontHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetFrontHeader {

    override suspend fun invoke(
        nroFront: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setFrontHeader(nroFront.toInt())
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