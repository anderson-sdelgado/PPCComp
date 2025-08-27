package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Section

data class SectionRetrofitModel(
    val idSection: Int,
    val codSection: Int
)

fun SectionRetrofitModel.retrofitModelToEntity(): Section {
    return with(this) {
        Section(
            idSection = idSection,
            codSection = codSection,
        )
    }
}

