package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.PlotRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.PlotRetrofitModel
import javax.inject.Inject

class IPlotRetrofitDatasource @Inject constructor(

): PlotRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<PlotRetrofitModel>> {
        TODO("Not yet implemented")
    }

}