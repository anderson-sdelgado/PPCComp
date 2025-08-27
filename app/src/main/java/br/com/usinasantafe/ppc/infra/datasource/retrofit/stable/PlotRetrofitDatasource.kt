package br.com.usinasantafe.ppc.infra.datasource.retrofit.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.PlotRetrofitModel

interface PlotRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<PlotRetrofitModel>>
}