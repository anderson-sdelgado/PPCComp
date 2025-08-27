package br.com.usinasantafe.ppc.external.retrofit.api.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.SectionRetrofitModel
import br.com.usinasantafe.ppc.utils.WEB_ALL_SECTION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SectionApi {

    @GET(WEB_ALL_SECTION)
    suspend fun all(@Header("Authorization") auth: String): Response<List<SectionRetrofitModel>>
}