package br.com.usinasantafe.ppc.domain.usecases.sample

import javax.inject.Inject

interface DeleteSample {
    suspend operator fun invoke(
        idSample: Int
    ): Result<Boolean>
}

class IDeleteSample @Inject constructor(
): DeleteSample {

    override suspend fun invoke(
        idSample: Int
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}