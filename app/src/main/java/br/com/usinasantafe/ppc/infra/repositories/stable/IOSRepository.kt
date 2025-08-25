package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import javax.inject.Inject

class IOSRepository @Inject constructor(

): OSRepository {

    override suspend fun addAll(list: List<OS>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(token: String): Result<List<OS>> {
        TODO("Not yet implemented")
    }

}