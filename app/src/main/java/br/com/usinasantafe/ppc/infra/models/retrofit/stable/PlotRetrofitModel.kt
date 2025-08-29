package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot

data class PlotRetrofitModel(
    val idPlot: Int,
    val nroPlot: Int,
    val idSection: Int
)

fun PlotRetrofitModel.retrofitModelToEntity(): Plot {
    return with(this) {
        Plot(
            idPlot = idPlot,
            nroPlot = nroPlot,
            idSection = idSection,
        )
    }
}
