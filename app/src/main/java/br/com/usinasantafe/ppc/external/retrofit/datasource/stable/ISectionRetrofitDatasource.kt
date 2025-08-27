package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.SectionRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.SectionRetrofitModel
import javax.inject.Inject

class ISectionRetrofitDatasource @Inject constructor(

): SectionRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<SectionRetrofitModel>> {
        TODO("Not yet implemented")
    }

}