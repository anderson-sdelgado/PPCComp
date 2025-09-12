package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.usecases.config.GetToken
import br.com.usinasantafe.ppc.presenter.model.ResultCheckDataWebServiceModel
import br.com.usinasantafe.ppc.utils.CheckNetwork
import br.com.usinasantafe.ppc.utils.StatusCon
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import java.net.SocketTimeoutException
import javax.inject.Inject

interface CheckOS {
    suspend operator fun invoke(
        nroOS: String
    ): Result<ResultCheckDataWebServiceModel>
}

class ICheckOS @Inject constructor(
    private val checkNetwork: CheckNetwork,
    private val getToken: GetToken,
    private val osRepository: OSRepository,
): CheckOS {

    override suspend fun invoke(
        nroOS: String
    ): Result<ResultCheckDataWebServiceModel> {
        try {
            val resultDelete = osRepository.deleteAll()
            if (resultDelete.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultDelete.exceptionOrNull()!!
                )
            }
            if (!checkNetwork.isConnected()) return Result.success(
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.WITHOUT
                )
            )
            val resultGetToken = getToken()
            if (resultGetToken.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetToken.exceptionOrNull()!!
                )
            }
            val token = resultGetToken.getOrNull()!!
            val resultGet = osRepository.getByNroOS(
                token = token,
                nroOS = nroOS.toInt()
            )
            if (resultGet.isFailure) {
                val error = resultGet.exceptionOrNull()!!
                if(error.cause is SocketTimeoutException) {
                    return Result.success(
                        ResultCheckDataWebServiceModel(
                            statusCon = StatusCon.SLOW
                        )
                    )
                }
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = error
                )
            }
            val entity = resultGet.getOrNull()!!
            if (entity.nroOS == 0) return Result.success(
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = false
                )
            )
            val resultAdd = osRepository.add(entity)
            if (resultAdd.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultAdd.exceptionOrNull()!!
                )
            }
            return Result.success(
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = true
                )
            )
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}