package br.com.usinasantafe.ppc.domain.entities.variable

data class Sample(
    var id: Int? = null,
    var idHeader: Int? = null,
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
