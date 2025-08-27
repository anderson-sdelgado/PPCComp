package br.com.usinasantafe.ppc.infra.models.room.stable

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.usinasantafe.ppc.domain.entities.stable.Section
import br.com.usinasantafe.ppc.utils.TB_SECTION

@Entity(tableName = TB_SECTION)
data class SectionRoomModel(
    @PrimaryKey
    val idSection: Int,
    val codSection: Int
)

fun SectionRoomModel.roomModelToEntity(): Section {
    return with(this) {
        Section(
            idSection = idSection,
            codSection = codSection,
        )
    }
}
fun Section.entityToRoomModel(): SectionRoomModel {
    return with(this) {
        SectionRoomModel(
            idSection = idSection,
            codSection = codSection,
        )
    }
}