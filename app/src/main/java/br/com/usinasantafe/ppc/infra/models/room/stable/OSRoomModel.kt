package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.utils.TB_OS

@Entity(tableName = TB_OS)
data class OSRoomModel(
    @PrimaryKey
    val nroOS: Int,
    val idSection: Int
)

fun OSRoomModel.roomModelToEntity(): OS {
    return with(this) {
        OS(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}

fun OS.entityToRoomModel(): OSRoomModel {
    return with(this) {
        OSRoomModel(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}
