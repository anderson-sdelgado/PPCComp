package br.com.usinasantafe.ppc.infra.models.retrofit.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel

data class OSRetrofitModel(
    val nroOS: Int,
    val idSection: Int
)

fun OSRetrofitModel.retrofitModelToEntity(): OS {
    return with(this) {
        OS(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}

fun OS.toSharedPreferencesModel(): OSSharedPreferencesModel {
    return with(this) {
        OSSharedPreferencesModel(
            nroOS = nroOS,
            idSection = idSection,
        )
    }
}
