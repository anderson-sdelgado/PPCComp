package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Section

interface SectionRepository {
    suspend fun addAll(list: List<Section>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Section>>
}