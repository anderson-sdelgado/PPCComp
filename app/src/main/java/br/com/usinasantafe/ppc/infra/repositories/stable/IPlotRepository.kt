package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot
import br.com.usinasantafe.ppc.domain.repositories.stable.PlotRepository
import javax.inject.Inject

class IPlotRepository @Inject constructor(

): PlotRepository {

    override suspend fun addAll(list: List<Plot>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(token: String): Result<List<Plot>> {
        TODO("Not yet implemented")
    }

}