package br.com.usinasantafe.ppc.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.variable.Sample
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.TB_SAMPLE

@Entity(tableName = TB_SAMPLE)
data class SampleRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val tare: Long,
    val billet: Long,
    val wholeCane: Long,
    val stump: Long,
    val chunk: Long,
    val pointer: Long,
    val chips: Long,
    val stone: Boolean,
    val treeStump: Boolean,
    val weed: Boolean,
    val anthill: Boolean,
    val guineaGrass: Boolean,
    val castorOilPlant: Boolean,
    val signalGrass: Boolean,
    val mucuna: Boolean,
    val silkGrass: Boolean,
    var statusSend: StatusSend = StatusSend.SEND,
)

fun SampleRoomModel.roomModelToEntity(): Sample {
    return with(this) {
        Sample(
            id = id,
            tare = tare,
            billet = billet,
            wholeCane = wholeCane,
            stump = stump,
            chunk = chunk,
            pointer = pointer,
            chips = chips,
            stone = stone,
            treeStump = treeStump,
            weed = weed,
            anthill = anthill,
            guineaGrass = guineaGrass,
            castorOilPlant = castorOilPlant,
            signalGrass = signalGrass,
            mucuna = mucuna,
            silkGrass = silkGrass,
        )
    }
}

fun Sample.entityToRoomModel(): SampleRoomModel {
    return with(this) {
        SampleRoomModel(
            id = id,
            tare = tare!!,
            billet = billet!!,
            wholeCane = wholeCane!!,
            stump = stump!!,
            chunk = chunk!!,
            pointer = pointer!!,
            chips = chips!!,
            stone = stone!!,
            treeStump = treeStump!!,
            weed = weed!!,
            anthill = anthill!!,
            guineaGrass = guineaGrass!!,
            castorOilPlant = castorOilPlant!!,
            signalGrass = signalGrass!!,
            mucuna = mucuna!!,
            silkGrass = silkGrass!!,
        )
    }
}