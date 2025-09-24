package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckWeightRelationTare {
    suspend operator fun invoke(
        value: String
    ): Result<Boolean>
}

class ICheckWeightRelationTare @Inject constructor(
    private val analysisRepository: AnalysisRepository
): CheckWeightRelationTare {

    override suspend fun invoke(
        value: String
    ): Result<Boolean> {
        try {
            val resultGetTare = analysisRepository.getTareSample()
            if (resultGetTare.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetTare.exceptionOrNull()!!
                )
            }
            val tare = resultGetTare.getOrNull()!!
            val valueDouble = value.replace(',','.') .toDouble()
            return Result.success(valueDouble >= tare)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}