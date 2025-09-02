package br.com.usinasantafe.ppc.infra.models.sharedpreferences

import java.util.Date

data class HeaderSharedPreferencesModel(
    var regAuditor1: Long? = null,
    var regAuditor2: Long? = null,
    var regAuditor3: Long? = null,
    var date: Date? = null,
    var nroTurn: Int? = null,
    var codSection: Int? = null,
    var nroPlot: Int? = null,
    var nroOS: Int? = null,
    var codFront: Int? = null,
    var nroHarvester: Int? = null,
    var regOperator: Long? = null
)
