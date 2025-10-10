package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.DownloadAndInstallApk
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateApp {
    suspend operator fun invoke(): Flow<Result<Float>>
}

class IUpdateApp @Inject constructor(
    private val configRepository: ConfigRepository,
    private val downloadAndInstallApk: DownloadAndInstallApk
): UpdateApp {

    override suspend fun invoke(): Flow<Result<Float>> = flow {
        val resultGet = configRepository.get()
        if (resultGet.isFailure) {
            emit(
                resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGet.exceptionOrNull()!!
                )
            )
            return@flow
        }
        val entity = resultGet.getOrNull()!!
        val version = "ppc_v_" + entity.version!!.replace('.', '_')
        downloadAndInstallApk(
            url = version
        ).collect { result ->
            emit(result)
        }
    }

}