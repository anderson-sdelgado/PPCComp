package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot

data class PlotRetrofitModel(
    val idPlot: Int,
    val codPlot: Int,
    val idSection: Int
)

fun PlotRetrofitModel.retrofitModelToEntity(): Plot {
    return with(this) {
        Plot(
            idPlot = idPlot,
            codPlot = codPlot,
            idSection = idSection,
        )
    }
}
