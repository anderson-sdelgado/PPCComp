package br.com.usinasantafe.ppc.domain.usecases.analysis

import javax.inject.Inject

interface CloseAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class ICloseAnalysis @Inject constructor(
): CloseAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}