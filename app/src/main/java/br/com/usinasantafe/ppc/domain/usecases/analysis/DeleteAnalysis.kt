package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface DeleteAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class IDeleteAnalysis @Inject constructor(
    private val analysisRepository: AnalysisRepository
): DeleteAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGet = analysisRepository.getIdHeaderByStatus(Status.OPEN)
            if (resultGet.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val id = resultGet.getOrNull()!!
            val resultDeleteSample = analysisRepository.deleteSampleByIdHeader(id)
            if (resultDeleteSample.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultDeleteSample.exceptionOrNull()!!
                )
            }
            val resultDeleteHeader = analysisRepository.deleteHeaderById(id)
            if (resultDeleteHeader.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultDeleteHeader.exceptionOrNull()!!
                )
            }
            return resultDeleteHeader
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}