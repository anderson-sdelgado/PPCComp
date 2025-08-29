package br.com.usinasantafe.ppc.domain.entities.variable

import java.util.Date

data class Header(
    val id: Int? = null,
    val regAuditor1: Long? = null,
    val regAuditor2: Long? = null,
    val regAuditor3: Long? = null,
    val date: Date? = null,
    val nroTurn: Int? = null,
    val codSection: Int? = null,
    val nroPlot: Int? = null,
    val nroOS: Int? = null,
    val codFront: Int? = null,
    val nroHarvester: Int? = null,
    val regOperator: Long? = null,
)
