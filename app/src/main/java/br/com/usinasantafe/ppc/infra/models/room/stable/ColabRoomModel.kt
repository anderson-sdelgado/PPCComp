package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.stable.Colab
import br.com.usinasantafe.ppc.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel(
    @PrimaryKey
    val regColab: Long,
)

fun ColabRoomModel.roomModelToEntity(): Colab {
    return with(this) {
        Colab(
            regColab = regColab,
        )
    }
}

fun Colab.entityToRoomModel(): ColabRoomModel {
    return with(this) {
        ColabRoomModel(
            regColab = regColab,
        )
    }
}