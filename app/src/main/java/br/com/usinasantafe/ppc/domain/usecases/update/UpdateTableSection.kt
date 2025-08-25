package br.com.usinasantafe.ppc.domain.usecases.update

import javax.inject.Inject

interface UpdateTableSection {
    suspend operator fun invoke(): Result<Boolean>
}

class IUpdateTableSection @Inject constructor(
): UpdateTableSection {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}