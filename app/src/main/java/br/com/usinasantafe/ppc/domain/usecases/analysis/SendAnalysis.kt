package br.com.usinasantafe.ppc.domain.usecases.analysis

import javax.inject.Inject

interface SendAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class ISendAnalysis @Inject constructor(
): SendAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}