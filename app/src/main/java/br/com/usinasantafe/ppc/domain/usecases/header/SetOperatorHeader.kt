package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetOperatorHeader {
    suspend operator fun invoke(
        regOperator: String
    ): Result<Boolean>
}

class ISetOperatorHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetOperatorHeader {

    override suspend fun invoke(
        regOperator: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setOperatorHeader(regOperator.toInt())
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