package br.com.usinasantafe.ppc.infra.models.sharedpreferences

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.StatusSend

data class ConfigSharedPreferencesModel(
    var number: Long? = null,
    var password: String? = null,
    var idBD: Int? = null,
    var version: String? = null,
    var statusSend: StatusSend = StatusSend.STARTED,
    var flagUpdate: FlagUpdate = FlagUpdate.OUTDATED,
)

fun ConfigSharedPreferencesModel.sharedPreferencesModelToEntity(): Config {
    return with(this) {
        Config(
            password = password,
            number = number,
            version = version,
            idBD = idBD,
            statusSend = statusSend,
            flagUpdate = flagUpdate,
        )
    }
}

fun Config.entityToSharedPreferencesModel(): ConfigSharedPreferencesModel {
    return with(this) {
        ConfigSharedPreferencesModel(
            password = password,
            number = number,
            version = version,
            idBD = idBD,
            statusSend = statusSend,
            flagUpdate = flagUpdate,
        )
    }
}
