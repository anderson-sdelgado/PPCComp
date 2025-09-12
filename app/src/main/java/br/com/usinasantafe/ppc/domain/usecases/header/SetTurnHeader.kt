package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetTurnHeader {
    suspend operator fun invoke(nroTurn: Int): Result<Boolean>
}

class ISetTurnHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetTurnHeader {

    override suspend fun invoke(nroTurn: Int): Result<Boolean> {
        val result = analysisRepository.setTurnHeader(nroTurn)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}