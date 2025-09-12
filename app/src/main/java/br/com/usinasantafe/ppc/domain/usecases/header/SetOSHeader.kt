package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetOSHeader {
    suspend operator fun invoke(nroOS: String): Result<Boolean>
}

class ISetOSHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetOSHeader {

    override suspend fun invoke(nroOS: String): Result<Boolean> {
        try {
            val result = analysisRepository.setOSHeader(nroOS.toInt())
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