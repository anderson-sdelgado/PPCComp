package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Colab

data class ColabRetrofitModel(
    val regColab: Long,
)

fun ColabRetrofitModel.retrofitModelToEntity(): Colab {
    return with(this) {
        Colab(
            regColab = regColab,
        )
    }
}
