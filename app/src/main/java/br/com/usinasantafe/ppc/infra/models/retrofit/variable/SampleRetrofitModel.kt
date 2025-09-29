package br.com.usinasantafe.ppc.infra.models.retrofit.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel

data class SampleRetrofitModelOutput(
    val id: Int,
    val pos: Int,
    val tare: Double,
    val stalk: Double?,
    val wholeCane: Double?,
    val stump: Double?,
    val piece: Double?,
    val tip: Double?,
    val slivers: Double?,
    val stone: Boolean,
    val treeStump: Boolean,
    val weed: Boolean,
    val anthill: Boolean,
    val guineaGrass: Boolean,
    val castorOilPlant: Boolean,
    val signalGrass: Boolean,
    val mucuna: Boolean,
    val silkGrass: Boolean,
)

data class SampleRetrofitModelInput(
    val id: Int,
    val idServ: Int,
)

fun SampleRoomModel.sampleRoomModelToRetrofitModel(): SampleRetrofitModelOutput {
    return with(this) {
        SampleRetrofitModelOutput(
            id = id!!,
            pos = pos,
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
