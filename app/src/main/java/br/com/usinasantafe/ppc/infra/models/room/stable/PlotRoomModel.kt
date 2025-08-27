package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.stable.Plot
import br.com.usinasantafe.ppc.utils.TB_PLOT

@Entity(tableName = TB_PLOT)
data class PlotRoomModel(
    @PrimaryKey
    val idPlot: Int,
    val codPlot: Int,
    val idSection: Int
)

fun PlotRoomModel.roomModelToEntity(): Plot {
    return with(this) {
        Plot(
            idPlot = idPlot,
            codPlot = codPlot,
            idSection = idSection,
        )
    }
}

fun Plot.entityToRoomModel(): PlotRoomModel {
    return with(this) {
        PlotRoomModel(
            idPlot = idPlot,
            codPlot = codPlot,
            idSection = idSection,
        )
    }
}