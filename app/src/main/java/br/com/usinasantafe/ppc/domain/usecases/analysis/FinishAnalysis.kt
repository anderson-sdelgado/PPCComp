package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface FinishAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class IFinishAnalysis @Inject constructor(
    private val analysisRepository: AnalysisRepository
): FinishAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetId = analysisRepository.getIdHeaderByStatus(Status.OPEN)
            if (resultGetId.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultFinish = analysisRepository.setStatusHeaderById(
                status = Status.FINISH,
                id = idHeader
            )
            if (resultFinish.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultFinish.exceptionOrNull()!!
                )
            }
            return Result.success(true)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}