package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Colab
import br.com.usinasantafe.ppc.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import javax.inject.Inject

class IColabRepository @Inject constructor(

): ColabRepository {

    override suspend fun addAll(list: List<Colab>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(token: String): Result<List<Colab>> {
        TODO("Not yet implemented")
    }

}