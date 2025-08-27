package br.com.usinasantafe.ppc.infra.datasource.retrofit.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.HarvesterRetrofitModel

interface HarvesterRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<HarvesterRetrofitModel>>
}