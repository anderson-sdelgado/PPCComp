package br.com.usinasantafe.ppc.infra.datasource.retrofit.stable

import br.com.usinasantafe.ppc.infra.models.retrofit.stable.SectionRetrofitModel

interface SectionRetrofitDatasource {
    suspend fun listAll(token: String): Result<List<SectionRetrofitModel>>
}