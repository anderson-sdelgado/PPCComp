package br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS

data class OSSharedPreferencesModel(
    val nroOS: Int,
    val idSection: Int
)

fun OSSharedPreferencesModel.sharedPreferencesModelToEntity(): OS {
    return with(this) {
        OS(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}

fun OS.entityToSharedPreferencesModel(): OSSharedPreferencesModel {
    return with(this) {
        OSSharedPreferencesModel(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}