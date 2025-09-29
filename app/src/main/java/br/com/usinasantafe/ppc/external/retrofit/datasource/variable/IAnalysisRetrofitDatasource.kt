package br.com.usinasantafe.ppc.external.retrofit.datasource.variable

import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.AnalysisRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import javax.inject.Inject

class IAnalysisRetrofitDatasource @Inject constructor(

): AnalysisRetrofitDatasource {
    override suspend fun send(
        token: String,
        retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Result<List<HeaderRetrofitModelInput>> {
        TODO("Not yet implemented")
    }
}