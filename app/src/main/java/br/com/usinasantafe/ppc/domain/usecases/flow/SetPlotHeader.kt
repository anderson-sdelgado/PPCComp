package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetPlotHeader {
    suspend operator fun invoke(
        nroPlot: String
    ): Result<Boolean>
}

class ISetPlotHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetPlotHeader {

    override suspend fun invoke(
        nroPlot: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setPlotHeader(nroPlot.toInt())
            if (result.isFailure) {
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