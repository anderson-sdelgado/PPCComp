package br.com.usinasantafe.ppc.domain.entities.variable

data class Sample(
    var id: Int? = null,
    var idHeader: Int? = null,
    var tare: Long? = null,
    var billet: Long? = null,
    var wholeCane: Long? = null,
    var stump: Long? = null,
    var chunk: Long? = null,
    var pointer: Long? = null,
    var chips: Long? = null,
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
