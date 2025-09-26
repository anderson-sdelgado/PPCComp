package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface SetSubObsSample {
    suspend operator fun invoke(
        guineaGrass: Boolean,
        castorOilPlant: Boolean,
        signalGrass: Boolean,
        mucuna: Boolean,
        silkGrass: Boolean
    ): Result<Boolean>
}

class ISetSubObsSample @Inject constructor(
    private val analysisRepository: AnalysisRepository
): SetSubObsSample {

    override suspend fun invoke(
        guineaGrass: Boolean,
        castorOilPlant: Boolean,
        signalGrass: Boolean,
        mucuna: Boolean,
        silkGrass: Boolean
    ): Result<Boolean> {
        val resultSet = analysisRepository.setSubObsSample(
            guineaGrass,
            castorOilPlant,
            signalGrass,
            mucuna,
            silkGrass
        )
        if(resultSet.isFailure){
            return resultFailure(
                context = getClassAndMethod(),
                cause = resultSet.exceptionOrNull()!!
            )
        }
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