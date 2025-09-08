package br.com.usinasantafe.ppc.external.retrofit.api.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.ppc.utils.WEB_GET_OS_BY_NRO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OSApi {
    
    @POST(WEB_GET_OS_BY_NRO)
    suspend fun getByNro(
        @Header("Authorization") auth: String,
        @Body nroOS: Int
    ): Response<OSRetrofitModel>

}