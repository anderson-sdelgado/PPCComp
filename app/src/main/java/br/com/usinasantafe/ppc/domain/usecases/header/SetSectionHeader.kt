package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetSectionHeader {
    suspend operator fun invoke(
        codSection: String,
    ): Result<Boolean>
}

class ISetSectionHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetSectionHeader {

    override suspend fun invoke(
        codSection: String,
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setSectionHeader(codSection.toInt())
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