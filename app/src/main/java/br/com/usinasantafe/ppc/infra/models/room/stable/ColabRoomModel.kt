package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.utils.TB_COLAB

@Entity(tableName = TB_COLAB)
data class ColabRoomModel(
    @PrimaryKey
    val regColab: Long,
    val nameColab: String,
)
