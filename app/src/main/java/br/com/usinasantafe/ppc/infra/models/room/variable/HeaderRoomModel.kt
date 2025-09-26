package br.com.usinasantafe.ppc.infra.models.room.variable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.TB_HEADER
import java.util.Date

@Entity(tableName = TB_HEADER)
data class HeaderRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val regAuditor1: Long,
    val regAuditor2: Long? = null,
    val regAuditor3: Long? = null,
    val date: Date,
    val nroTurn: Int,
    val codSection: Int,
    val nroPlot: Int,
    val nroOS: Int,
    val codFront: Int,
    val nroHarvester: Int,
    val regOperator: Long,
    val dateHour: Date = Date(),
    var status: Status = Status.CLOSE,
    var statusSend: StatusSend = StatusSend.STARTED,
    var idServ: Int? = null,
)

fun HeaderRoomModel.roomModelToEntity(): Header {
    return with(this) {
        Header(
            id = id,
            regAuditor1 = regAuditor1,
            regAuditor2 = regAuditor2,
            regAuditor3 = regAuditor3,
            date = date,
            nroTurn = nroTurn,
            codSection = codSection,
            nroPlot = nroPlot,
            nroOS = nroOS,
            codFront = codFront,
            nroHarvester = nroHarvester,
            regOperator = regOperator,
        )
    }
}

fun Header.entityToRoomModel(): HeaderRoomModel {
    return with(this) {
        HeaderRoomModel(
            id = id,
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
            regOperator = regOperator!!,
        )
    }
}