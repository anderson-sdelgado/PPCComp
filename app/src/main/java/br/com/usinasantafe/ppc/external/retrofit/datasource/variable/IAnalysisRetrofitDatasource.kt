package br.com.usinasantafe.ppc.external.retrofit.datasource.variable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.variable.AnalysisApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.AnalysisRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IAnalysisRetrofitDatasource @Inject constructor(
    private val analysisApi: AnalysisApi
): AnalysisRetrofitDatasource {
    override suspend fun send(
        token: String,
        retrofitModelOutputList: List<HeaderRetrofitModelOutput>
    ): Result<List<HeaderRetrofitModelInput>> {
        try {
            val response = analysisApi.send(
                token,
                retrofitModelOutputList
            )
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }
}