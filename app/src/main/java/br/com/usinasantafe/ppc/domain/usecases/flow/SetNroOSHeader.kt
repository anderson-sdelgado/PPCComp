package br.com.usinasantafe.ppc.domain.usecases.flow

import javax.inject.Inject

interface SetNroOSHeader {
    suspend operator fun invoke(nroOS: String): Result<Boolean>
}

class ISetNroOSHeader @Inject constructor(
): SetNroOSHeader {

    override suspend fun invoke(nroOS: String): Result<Boolean> {
        TODO("Not yet implemented")
    }

}