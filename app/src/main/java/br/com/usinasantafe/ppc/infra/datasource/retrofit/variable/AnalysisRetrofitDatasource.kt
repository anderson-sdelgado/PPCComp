package br.com.usinasantafe.ppc.infra.datasource.retrofit.variable

import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelOutput

interface AnalysisRetrofitDatasource {
    suspend fun send(
        token: String,
        retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Result<List<HeaderRetrofitModelInput>>
}