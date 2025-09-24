package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetObsSample {
    suspend operator fun invoke(
        stone: Boolean,
        treeStump: Boolean,
        weed: Boolean,
        anthill: Boolean
    ): Result<Boolean>
}

class ISetObsSample @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetObsSample {

    override suspend fun invoke(
        stone: Boolean,
        treeStump: Boolean,
        weed: Boolean,
        anthill: Boolean
    ): Result<Boolean> {
        val resultSet = analysisRepository.setObsSample(
            stone = stone,
            treeStump = treeStump,
            weed = weed,
            anthill = anthill
        )
        if(resultSet.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultSet.exceptionOrNull()!!
            )
        }
        if(!weed) return resultSet
        val resultSave = analysisRepository.saveSample()
        if(resultSave.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultSave.exceptionOrNull()!!
            )
        }
        return resultSave
    }

}