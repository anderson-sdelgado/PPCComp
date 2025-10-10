package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetStatusSend {
    suspend operator fun invoke(): Flow<Result<StatusSend>>
}

class IGetStatusSend @Inject constructor(
    private val configRepository: ConfigRepository,
    private val analysisRepository: AnalysisRepository
): GetStatusSend {

    override suspend fun invoke(): Flow<Result<StatusSend>> = flow {
        try {
            val resultHasConfig = configRepository.hasConfig()
            if (resultHasConfig.isFailure) {
                emit(
                    resultFailure(
                        context = getClassAndMethod(),
                        cause = resultHasConfig.exceptionOrNull()!!
                    )
                )
                return@flow
            }
            val hasConfig = resultHasConfig.getOrNull()!!
            if (!hasConfig) {
                emit(Result.success(StatusSend.STARTED))
                return@flow
            }
            val resultCheck = analysisRepository.checkSend()
            if (resultCheck.isFailure) {
                emit(
                    resultFailure(
                        context = getClassAndMethod(),
                        cause = resultCheck.exceptionOrNull()!!
                    )
                )
                return@flow
            }
            val statusCheck = resultCheck.getOrNull()!!
            if (!statusCheck) {
                emit(Result.success(StatusSend.SENT))
            } else {
                emit(Result.success(StatusSend.SEND))
            }
        } catch (e: Exception) {
            emit(
                resultFailure(
                    context = getClassAndMethod(),
                    cause = e
                )
            )
        }
    }

}