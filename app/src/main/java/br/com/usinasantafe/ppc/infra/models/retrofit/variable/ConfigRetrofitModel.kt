package br.com.usinasantafe.ppc.infra.models.retrofit.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config

data class ConfigRetrofitModelOutput(
    var number: Long,
    var version: String,
)

data class ConfigRetrofitModelInput(
    val idServ: Int,
    val versionUpdate: String,
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    require(number != 0L) { "The field 'number' cannot is null." }
    require(!version.isNullOrEmpty()) { "The field 'version' cannot is null or empty." }
    return ConfigRetrofitModelOutput(
        number = number!!,
        version = version!!
    )
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    require(idServ != 0) { "The field 'idServ' cannot is null." }
    require(versionUpdate.isNotEmpty()) { "The field 'versionUpdate' cannot is null." }
    return Config(
        idServ = idServ,
        versionUpdate = versionUpdate
    )
}
