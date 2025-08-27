package br.com.usinasantafe.ppc.external.retrofit.api.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.PlotRetrofitModel
import br.com.usinasantafe.ppc.utils.WEB_ALL_PLOT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface PlotApi {

    @GET(WEB_ALL_PLOT)
    suspend fun all(@Header("Authorization") auth: String): Response<List<PlotRetrofitModel>>
}