package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.PlotRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckPlot {
    suspend operator fun invoke(
        nroPlot: String
    ): Result<Boolean>
}

class ICheckPlot @Inject constructor(
    private val plotRepository: PlotRepository,
    private val analysisRepository: AnalysisRepository,
    private val sectionRepository: SectionRepository
): CheckPlot {

    override suspend fun invoke(
        nroPlot: String
    ): Result<Boolean> {
        try {
            val nro = nroPlot.toInt()
            val resultGetSection = analysisRepository.getSectionHeader()
            if (resultGetSection.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetSection.exceptionOrNull()!!
                )
            }
            val codSection = resultGetSection.getOrNull()!!
            val resultGetIdSection = sectionRepository.getIdByCod(codSection)
            if (resultGetIdSection.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdSection.exceptionOrNull()!!
                )
            }
            val idSection = resultGetIdSection.getOrNull()!!
            val resultCheck = plotRepository.checkByNroPlotAndIdSection(
                nroPlot = nro,
                idSection = idSection
            )
            if (resultCheck.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheck.exceptionOrNull()!!
                )
            }
            return resultCheck
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}