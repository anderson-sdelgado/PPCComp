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
    var stone: Boolean? = null,
    var treeStump: Boolean? = null,
    var weed: Boolean? = null,
    var anthill: Boolean? = null,
    var guineaGrass: Boolean? = null,
    var castorOilPlant: Boolean? = null,
    var signalGrass: Boolean? = null,
    var mucuna: Boolean? = null,
    var silkGrass: Boolean? = null,
)
