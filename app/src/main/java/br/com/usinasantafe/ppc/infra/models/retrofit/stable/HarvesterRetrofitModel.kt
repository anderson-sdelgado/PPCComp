package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester

data class HarvesterRetrofitModel(
    val nroHarvester: Int
)

fun HarvesterRetrofitModel.retrofitModelToEntity(): Harvester {
    return with(this) {
        Harvester(
            nroHarvester = nroHarvester,
        )
    }
}

