package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.external.retrofit.api.stable.SectionApi
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.SectionRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.SectionRetrofitModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class ISectionRetrofitDatasource @Inject constructor(
    private val sectionApi: SectionApi
): SectionRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<SectionRetrofitModel>> {
        try {
            val response = sectionApi.all(token)
            return Result.success(response.body()!!)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}