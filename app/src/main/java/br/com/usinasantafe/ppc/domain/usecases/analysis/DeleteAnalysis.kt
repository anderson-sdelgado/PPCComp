package br.com.usinasantafe.ppc.domain.usecases.analysis

import javax.inject.Inject

interface DeleteAnalysis {
    suspend operator fun invoke(): Result<Boolean>
}

class IDeleteAnalysis @Inject constructor(
): DeleteAnalysis {

    override suspend fun invoke(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}