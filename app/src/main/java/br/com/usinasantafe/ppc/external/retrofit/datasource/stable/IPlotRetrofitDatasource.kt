package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.stable.PlotApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.PlotRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.PlotRetrofitModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IPlotRetrofitDatasource @Inject constructor(
    private val plotApi: PlotApi
): PlotRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<PlotRetrofitModel>> {
        try {
            val response = plotApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}