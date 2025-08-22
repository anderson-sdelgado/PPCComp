package br.com.usinasantafe.ppc.domain.usecases.config

import javax.inject.Inject

interface SetFinishUpdateAllTable {
    suspend operator fun invoke(): Result<Boolean>
}

class ISetFinishUpdateAllTable @Inject constructor(
): SetFinishUpdateAllTable {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}