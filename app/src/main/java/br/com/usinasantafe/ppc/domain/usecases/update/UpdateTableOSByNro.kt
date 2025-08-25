package br.com.usinasantafe.ppc.domain.usecases.update

import javax.inject.Inject

interface UpdateTableOSByNro {
    suspend operator fun invoke(): Result<Boolean>
}

class IUpdateTableOSByNro @Inject constructor(
): UpdateTableOSByNro {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}