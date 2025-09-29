package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.domain.usecases.config.GetToken
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject
import kotlin.text.get

interface SendAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendAnalysis @Inject constructor(
    private val getToken: GetToken,
    private val configRepository: ConfigRepository,
    private val analysisRepository: AnalysisRepository
): SendAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        try {
            val resultGetToken = getToken()
            if (resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGetConfig = configRepository.get()
            if (resultGetConfig.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetConfig.exceptionOrNull()!!
                )
            }
            val config = resultGetConfig.getOrNull()!!
            val resultSend = analysisRepository.send(
                token = token,
                number = config.number!!
            )
            if (resultSend.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultSend.exceptionOrNull()!!
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