package br.com.usinasantafe.ppc.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.variable.Sample
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.TB_SAMPLE

@Entity(tableName = "tb_sample")
data class SampleRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val idHeader: Int,
    val tare: Double,
    val stalk: Double,
    val wholeCane: Double,
    val stump: Double,
    val piece: Double,
    val tip: Double,
    val slivers: Double,
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
            idHeader = idHeader,
            tare = tare,
            stalk = stalk,
            wholeCane = wholeCane,
            stump = stump,
            piece = piece,
            tip = tip,
            slivers = slivers,
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
            idHeader = idHeader!!,
            tare = tare!!,
            stalk = stalk!!,
            wholeCane = wholeCane!!,
            stump = stump!!,
            piece = piece!!,
            tip = tip!!,
            slivers = slivers!!,
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