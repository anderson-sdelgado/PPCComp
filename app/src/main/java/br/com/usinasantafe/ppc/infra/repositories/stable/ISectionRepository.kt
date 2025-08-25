package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Section
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import javax.inject.Inject

class ISectionRepository @Inject constructor(

): SectionRepository {
    override suspend fun addAll(list: List<Section>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(token: String): Result<List<Section>> {
        TODO("Not yet implemented")
    }

}