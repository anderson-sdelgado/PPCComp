package br.com.usinasantafe.ppc.external.retrofit.api.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.HarvesterRetrofitModel
import br.com.usinasantafe.ppc.utils.WEB_ALL_HARVESTER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface HarvesterApi {

    @GET(WEB_ALL_HARVESTER)
    suspend fun all(@Header("Authorization") auth: String): Response<List<HarvesterRetrofitModel>>

}