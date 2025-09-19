package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetFieldSample {
    suspend operator fun invoke(
        field: Field,
        value: String
    ): Result<Boolean>
}

class ISetFieldSample @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetFieldSample {

    override suspend fun invoke(
        field: Field,
        value: String
    ): Result<Boolean> {
        try {
            val result = analysisRepository.setFieldSample(
                field = field,
                value = value.replace(',','.') .toDouble()
            )
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