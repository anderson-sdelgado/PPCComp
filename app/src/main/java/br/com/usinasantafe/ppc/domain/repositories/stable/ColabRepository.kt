package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Colab

interface ColabRepository {
    suspend fun addAll(list: List<Colab>): Result<Boolean>
    suspend fun deleteAll(): Result<Boolean>
    suspend fun listAll(token: String): Result<List<Colab>>
}