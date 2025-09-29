package br.com.usinasantafe.ppc.infra.models.retrofit.variable

import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import java.util.Date

data class HeaderRetrofitModelOutput(
    val id: Int,
    val regAuditor1: Long,
    val regAuditor2: Long? = null,
    val regAuditor3: Long? = null,
    val date: String,
    val nroTurn: Int,
    val codSection: Int,
    val nroPlot: Int,
    val nroOS: Int,
    val codFront: Int,
    val nroHarvester: Int,
    val regOperator: Long,
    val dateHour: String,
    val number: Long,
    val sampleList: List<SampleRetrofitModelOutput>,
)

data class HeaderRetrofitModelInput(
    val id: Int,
    val idServ: Int,
    val sampleList: List<SampleRetrofitModelInput>,
)

fun HeaderRoomModel.headerRoomModelToRetrofitModel(
    number: Long,
    sampleList: List<SampleRetrofitModelOutput>,
): HeaderRetrofitModelOutput {
    return with(this) {
        HeaderRetrofitModelOutput(
            id = id!!,
            regAuditor1 = regAuditor1,
            regAuditor2 = regAuditor2,
            regAuditor3 = regAuditor3,
            date = date.toString(),
            nroTurn = nroTurn,
            codSection = codSection,
            nroPlot = nroPlot,
            nroOS = nroOS,
            codFront = codFront,
            nroHarvester = nroHarvester,
            regOperator = regOperator,
            dateHour = dateHour.toString(),
            number = number,
            sampleList = sampleList,
        )
    }
}