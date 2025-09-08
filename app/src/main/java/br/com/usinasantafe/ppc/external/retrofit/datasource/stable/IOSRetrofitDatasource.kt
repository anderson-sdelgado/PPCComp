package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class IOSRetrofitDatasource @Inject constructor(
    private val osApi: OSApi
): OSRetrofitDatasource {

    override suspend fun getByNroOS(
        token: String,
        nroOS: Int
    ): Result<OSRetrofitModel> {
        try {
            val response = osApi.getByNro(
                auth = token,
                nroOS = nroOS
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