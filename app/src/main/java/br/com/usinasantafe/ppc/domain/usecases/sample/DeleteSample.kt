package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface DeleteSample {
    suspend operator fun invoke(
        id: Int
    ): Result<Boolean>
}

class IDeleteSample @Inject constructor(
    private val analysisRepository: AnalysisRepository
): DeleteSample {

    override suspend fun invoke(
        id: Int
    ): Result<Boolean> {
        val result = analysisRepository.deleteSampleById(id)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}