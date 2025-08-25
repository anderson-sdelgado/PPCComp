package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
import javax.inject.Inject

class IHarvesterRepository @Inject constructor(

): HarvesterRepository {

    override suspend fun addAll(list: List<Harvester>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(token: String): Result<List<Harvester>> {
        TODO("Not yet implemented")
    }

}