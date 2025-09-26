package br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel

data class SampleSharedPreferencesModel(
    var tare: Double? = null,
    var stalk: Double? = null,
    var wholeCane: Double? = null,
    var stump: Double? = null,
    var piece: Double? = null,
    var tip: Double? = null,
    var slivers: Double? = null,
    var stone: Boolean = false,
    var treeStump: Boolean = false,
    var weed: Boolean = false,
    var anthill: Boolean = false,
    var guineaGrass: Boolean = false,
    var castorOilPlant: Boolean = false,
    var signalGrass: Boolean = false,
    var mucuna: Boolean = false,
    var silkGrass: Boolean = false,
)

fun SampleSharedPreferencesModel.sharedPreferencesModelToRoomModel(
    idHeader: Int
): SampleRoomModel {
    val nonNullTare = requireNotNull(tare) { "Field 'tare' cannot be null." }
    return with(this) {
        SampleRoomModel(
            idHeader = idHeader,
            tare = nonNullTare,
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
            silkGrass = silkGrass
        )
    }
}