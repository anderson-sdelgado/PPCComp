package br.com.usinasantafe.ppc.external.retrofit.datasource.variable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.variable.ConfigApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.ConfigRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.ConfigRetrofitModelOutput
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IConfigRetrofitDatasource @Inject constructor(
    private val configApi: ConfigApi
): ConfigRetrofitDatasource {

    override suspend fun recoverToken(retrofitModelOutput: ConfigRetrofitModelOutput): Result<ConfigRetrofitModelInput> {
        try {
            val response = configApi.send(
                retrofitModelOutput
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