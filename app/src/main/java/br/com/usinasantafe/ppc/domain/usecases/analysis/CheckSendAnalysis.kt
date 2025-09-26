package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSendAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class ICheckSendAnalysis @Inject constructor(
    private val analysisRepository: AnalysisRepository
): CheckSendAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        val result = analysisRepository.checkSend()
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result

    }

}