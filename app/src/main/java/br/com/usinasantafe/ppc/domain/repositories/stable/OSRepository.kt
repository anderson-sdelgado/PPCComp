package br.com.usinasantafe.ppc.domain.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS

interface OSRepository {
    suspend fun deleteAll(): Result<Boolean>
    suspend fun getByNroOS(
        token: String,
        nroOS: Int,
    ): Result<OS>
    suspend fun add(entity: OS): Result<Boolean>
    suspend fun checkSectionAndOS(
        idSection: Int,
        nroOS: Int
    ): Result<Boolean>
}