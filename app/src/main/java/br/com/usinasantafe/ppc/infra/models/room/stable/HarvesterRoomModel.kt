package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.utils.TB_HARVESTER

@Entity(tableName = TB_HARVESTER)
data class HarvesterRoomModel(
    @PrimaryKey
    val nroHarvester: Int
)

fun HarvesterRoomModel.roomModelToEntity(): Harvester {
    return with(this) {
        Harvester(
            nroHarvester = nroHarvester,
        )
    }
}

fun Harvester.entityToRoomModel(): HarvesterRoomModel {
    return with(this) {
        HarvesterRoomModel(
            nroHarvester = nroHarvester,
        )
    }
}
