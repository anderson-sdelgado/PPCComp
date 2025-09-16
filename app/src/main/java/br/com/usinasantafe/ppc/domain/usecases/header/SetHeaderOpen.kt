package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetHeaderOpen {
    suspend operator fun invoke(
        id: Int,
    ): Result<Boolean>
}

class ISetHeaderOpen @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetHeaderOpen {

    override suspend fun invoke(
        id: Int,
    ): Result<Boolean> {
        val result = analysisRepository.setStatusHeaderById(
            status = Status.OPEN,
            id = id
        )
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}