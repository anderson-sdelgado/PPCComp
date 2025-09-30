package br.com.usinasantafe.ppc.external.retrofit.api.variable

import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.ppc.utils.WEB_SAVE_ANALYSIS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AnalysisApi {

    @POST(WEB_SAVE_ANALYSIS)
    suspend fun send(
        @Header("Authorization") auth: String,
        @Body list: List<HeaderRetrofitModelOutput>
    ): Response<List<HeaderRetrofitModelInput>>

}