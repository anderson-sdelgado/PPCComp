package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.presenter.model.HeaderScreenModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface ListHeader {
    suspend operator fun invoke(): Result<List<HeaderScreenModel>>
}

class IListHeader @Inject constructor(
    private val analysisRepository: AnalysisRepository
): ListHeader {

    override suspend fun invoke(): Result<List<HeaderScreenModel>> {
        try {
            val resultList = analysisRepository.listHeader()
            if(resultList.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultList.exceptionOrNull()!!
                )
            }
            val entityList = resultList.getOrNull()!!
            val modelList = entityList.map { header ->
                val resultCountSample = analysisRepository.countSampleByIdHeader(header.id!!)
                if(resultCountSample.isFailure) {
                    return resultFailure(
                        context = getClassAndMethod(),
                        cause = resultCountSample.exceptionOrNull()!!
                    )
                }
                val count = resultCountSample.getOrNull()!!
                HeaderScreenModel(
                    id = header.id,
                    harvester = header.nroHarvester!!,
                    turn = header.nroTurn!!,
                    operator = header.regOperator!!,
                    front = header.codFront!!,
                    qtdSample = count
                )
            }
            return Result.success(modelList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}