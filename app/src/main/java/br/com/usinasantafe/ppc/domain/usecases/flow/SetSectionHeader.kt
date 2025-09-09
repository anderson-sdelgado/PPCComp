package br.com.usinasantafe.ppc.domain.usecases.flow

import javax.inject.Inject

interface SetSectionHeader {
    suspend operator fun invoke(
        nroSection: String,
    ): Result<Boolean>
}

class ISetSectionHeader @Inject constructor(
): SetSectionHeader {

    override suspend fun invoke(
        nroSection: String,
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}