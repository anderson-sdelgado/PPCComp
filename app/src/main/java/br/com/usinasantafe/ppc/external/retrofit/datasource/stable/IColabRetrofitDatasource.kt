package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IColabRetrofitDatasource @Inject constructor(
    private val colabApi: ColabApi
): ColabRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<ColabRetrofitModel>> {
        try {
            val response = colabApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }


}