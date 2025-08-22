package br.com.usinasantafe.ppc.domain.usecases.update

import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface UpdateTableColab {
    suspend operator fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel>
}

class IUpdateTableColab @Inject constructor(
): UpdateTableColab {

    override suspend fun invoke(
        sizeAll: Float,
        count: Float
    ): Flow<ResultUpdateModel> = flow {
        TODO("Not yet implemented")
    }

}