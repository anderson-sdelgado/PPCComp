package br.com.usinasantafe.ppc.infra.datasource.retrofit.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.ColabRetrofitModel

interface ColabRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<ColabRetrofitModel>>
}