package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.util.Date
import javax.inject.Inject

interface SetDateHeader {
    suspend operator fun invoke(date: Date): Result<Boolean>
}

class ISetDateHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetDateHeader {

    override suspend fun invoke(date: Date): Result<Boolean> {
        val result = analysisRepository.setDateHeader(date)
        if(result.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}