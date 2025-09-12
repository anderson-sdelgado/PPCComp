package br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
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

fun HeaderSharedPreferencesModel.sharedPreferencesModelToRoomModel(): HeaderRoomModel {
    return with(this) {
        HeaderRoomModel(
            regAuditor1 = regAuditor1!!,
            regAuditor2 = regAuditor2,
            regAuditor3 = regAuditor3,
            date = date!!,
            nroTurn = nroTurn!!,
            codSection = codSection!!,
            nroPlot = nroPlot!!,
            nroOS = nroOS!!,
            codFront = codFront!!,
            nroHarvester = nroHarvester!!,
            regOperator = regOperator!!
        )
    }
}
