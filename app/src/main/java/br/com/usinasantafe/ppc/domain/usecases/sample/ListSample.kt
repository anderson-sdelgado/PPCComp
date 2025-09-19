package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface ListSample {
    suspend operator fun invoke(): Result<List<SampleScreenModel>>
}

class IListSample @Inject constructor(
    private val analysisRepository: AnalysisRepository
): ListSample {

    override suspend fun invoke(): Result<List<SampleScreenModel>> {
        try {
            val resultGetId = analysisRepository.getIdHeaderByStatus(Status.OPEN)
            if (resultGetId.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetId.exceptionOrNull()!!
                )
            }
            val idHeader = resultGetId.getOrNull()!!
            val resultList = analysisRepository.listSampleByIdHeader(idHeader)
            if (resultList.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            val list = resultList.getOrNull()!!
            val listScreenModel = list.map {
                var obs = if(it.stone) "PEDRA - " else ""
                obs += if(it.treeStump) "TOCO DE ARVORE - " else ""
                obs += if(it.anthill) "FORMIGUEIROS - " else ""
                obs += if(it.weed) "PLANTA DANINHA - " else ""
                obs += if(it.guineaGrass) "CAPIM-COLONIÃO - " else ""
                obs += if(it.castorOilPlant) "MAMONA - " else ""
                obs += if(it.signalGrass) "CAPIM-BRAQUIÁRIA - " else ""
                obs += if(it.mucuna) "MUCUNA - " else ""
                obs += if(it.silkGrass) "GRAMA-SEDA - " else ""
                obs = obs.removeSuffix(" - ")
                SampleScreenModel(
                    id = it.id!!,
                    stalk = it.stalk!!,
                    wholeCane = it.wholeCane!!,
                    stump = it.stump!!,
                    piece = it.piece!!,
                    tip = it.tip!!,
                    slivers = it.slivers!!,
                    obs = obs
                )
            }
            return Result.success(listScreenModel)
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}