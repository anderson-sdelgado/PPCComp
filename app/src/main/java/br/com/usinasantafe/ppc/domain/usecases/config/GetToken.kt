package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import br.com.usinasantafe.ppc.utils.token
import javax.inject.Inject
import kotlin.text.get

interface GetToken {
    suspend operator fun invoke(): Result<String>
}

class IGetToken @Inject constructor(
    private val configRepository: ConfigRepository
): GetToken {

    override suspend fun invoke(): Result<String> {
        try {
            val resultGet = configRepository.get()
            if (resultGet.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            }
            val entity = resultGet.getOrNull()!!
            val token = token(
                idServ = entity.idServ!!,
                number = entity.number!!,
                version = entity.version!!
            )
            return Result.success(token)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}