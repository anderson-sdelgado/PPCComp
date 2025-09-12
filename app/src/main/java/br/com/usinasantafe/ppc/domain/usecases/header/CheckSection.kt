package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSection {
    suspend operator fun invoke(
        codSection: String
    ): Result<Boolean>
}

class ICheckSection @Inject constructor(
    private val sectionRepository: SectionRepository,
    private val osRepository: OSRepository,
    private val analysisRepository: AnalysisRepository
): CheckSection {

    override suspend fun invoke(
        codSection: String
    ): Result<Boolean> {
        try {
            val resultCheckNro = sectionRepository.checkCod(codSection.toInt())
            if(resultCheckNro.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckNro.exceptionOrNull()!!
                )
            }
            val check = resultCheckNro.getOrNull()!!
            if(!check) return Result.success(false)
            val resultGetIdSection = sectionRepository.getIdByCod(codSection.toInt())
            if(resultGetIdSection.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdSection.exceptionOrNull()!!
                )
            }
            val idSection = resultGetIdSection.getOrNull()!!
            val resultGetOS = analysisRepository.getOSHeaderOpen()
            if(resultGetOS.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetOS.exceptionOrNull()!!
                )
            }
            val nroOS = resultGetOS.getOrNull()!!
            val resultCheckIdSection = osRepository.checkSectionAndOS(
                idSection = idSection,
                nroOS = nroOS
            )
            if(resultCheckIdSection.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckIdSection.exceptionOrNull()!!
                )
            }
            return resultCheckIdSection
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}