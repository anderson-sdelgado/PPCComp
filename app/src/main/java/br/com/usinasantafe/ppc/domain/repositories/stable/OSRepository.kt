package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS

interface OSRepository {
    suspend fun addAll(list: List<OS>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<OS>>
}