package br.com.usinasantafe.ppc.presenter.model

data class HeaderScreenModel(
    val id: Int,
    val turn: Int,
    val front: Int,
    val harvester: Int,
    val operator: Long,
    val qtdSample: Int
)
