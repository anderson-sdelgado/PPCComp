package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetAuditor {
    suspend operator fun invoke(
        pos: Int,
        regAuditor: String
    ): Result<Boolean>
}

class ISetAuditor @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetAuditor {

    override suspend fun invoke(
        pos: Int,
        regAuditor: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setAuditor(
                pos = pos,
                regAuditor = regAuditor.toInt()
            )
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