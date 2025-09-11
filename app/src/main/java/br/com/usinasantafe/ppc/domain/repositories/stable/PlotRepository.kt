package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot

interface PlotRepository {
    suspend fun addAll(list: List<Plot>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Plot>>
    suspend fun checkByNroPlotAndIdSection(
        nroPlot: Int,
        idSection: Int
    ): Result<Boolean>
}