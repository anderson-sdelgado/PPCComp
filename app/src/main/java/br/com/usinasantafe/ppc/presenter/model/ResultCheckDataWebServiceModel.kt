package br.com.usinasantafe.ppc.presenter.model

import br.com.usinasantafe.ppc.utils.StatusCon

data class ResultCheckDataWebServiceModel(
    val statusCon: StatusCon,
    val check: Boolean? = null,
)
