package br.com.usinasantafe.ppc.infra.models.retrofit.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config

data class ConfigRetrofitModelOutput(
    var number: Long,
    var version: String,
)

data class ConfigRetrofitModelInput(
    val idServ: Int
)

fun Config.entityToRetrofitModel(): ConfigRetrofitModelOutput {
    require(number != 0L) { "The field 'number' cannot is null." }
    return ConfigRetrofitModelOutput(
        number = number!!,
        version = version!!
    )
}

fun ConfigRetrofitModelInput.retrofitToEntity(): Config {
    require(idServ != 0) { "The field 'idServ' cannot is null." }
    return Config(
        idServ = idServ,
    )
}