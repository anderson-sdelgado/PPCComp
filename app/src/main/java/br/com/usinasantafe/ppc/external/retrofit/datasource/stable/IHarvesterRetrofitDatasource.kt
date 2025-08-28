package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.stable.HarvesterApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.HarvesterRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.HarvesterRetrofitModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IHarvesterRetrofitDatasource @Inject constructor(
    private val harvesterApi: HarvesterApi
): HarvesterRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<HarvesterRetrofitModel>> {
        try {
            val response = harvesterApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}