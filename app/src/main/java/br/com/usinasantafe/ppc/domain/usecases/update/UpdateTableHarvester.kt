package br.com.usinasantafe.ppc.domain.usecases.update

import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
import br.com.usinasantafe.ppc.domain.usecases.config.GetToken
import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.LevelUpdate
import br.com.usinasantafe.ppc.utils.TB_HARVESTER
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import br.com.usinasantafe.ppc.utils.updatePercentage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableHarvester {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableHarvester @Inject constructor(
    private val getToken: GetToken,
    private val harvesterRepository: HarvesterRepository,
): UpdateTableHarvester {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(1f, count, sizeAll),
                tableUpdate = TB_HARVESTER,
                levelUpdate = LevelUpdate.RECOVERY
            )
        )
        val resultGetToken = getToken()
        if (resultGetToken.isFailure) {
            val error = resultGetToken.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            return@flow
        }
        val token = resultGetToken.getOrNull()!!
        val resultRecoverAll = harvesterRepository.listAll(token)
        if (resultRecoverAll.isFailure) {
            val error = resultRecoverAll.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(2f, count, sizeAll),
                tableUpdate = TB_HARVESTER,
                levelUpdate = LevelUpdate.CLEAN
            )
        )
        val resultDeleteAll = harvesterRepository.deleteAll()
        if (resultDeleteAll.isFailure) {
            val error = resultDeleteAll.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
        emit(
            ResultUpdateModel(
                flagProgress = true,
                currentProgress = updatePercentage(3f, count, sizeAll),
                tableUpdate = TB_HARVESTER,
                levelUpdate = LevelUpdate.SAVE
            )
        )
        val entityList = resultRecoverAll.getOrNull()!!
        val resultAddAll = harvesterRepository.addAll(entityList)
        if (resultAddAll.isFailure) {
            val error = resultAddAll.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            emit(
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = failure,
                    levelUpdate = null,
                    currentProgress = 1f,
                )
            )
            return@flow
        }
    }

}