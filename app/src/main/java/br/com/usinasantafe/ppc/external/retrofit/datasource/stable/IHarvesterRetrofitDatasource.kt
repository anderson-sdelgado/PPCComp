package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.HarvesterRetrofitDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.HarvesterRetrofitModel
import javax.inject.Inject

class IHarvesterRetrofitDatasource @Inject constructor(

): HarvesterRetrofitDatasource {

    override suspend fun listAll(token: String): Result<List<HarvesterRetrofitModel>> {
        TODO("Not yet implemented")
    }

}