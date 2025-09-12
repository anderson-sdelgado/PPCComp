package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester

interface HarvesterRepository {
    suspend fun addAll(list: List<Harvester>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Harvester>>
    suspend fun checkNroHarvester(nroHarvester: Int): Result<Boolean>
}