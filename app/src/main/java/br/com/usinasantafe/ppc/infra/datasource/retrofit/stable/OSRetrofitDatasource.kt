package br.com.usinasantafe.ppc.infra.datasource.retrofit.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.OSRetrofitModel

interface OSRetrofitDatasource {
    suspend fun getByNroOS(
        token: String,
        nroOS: Int
    ): Result<OSRetrofitModel>
}