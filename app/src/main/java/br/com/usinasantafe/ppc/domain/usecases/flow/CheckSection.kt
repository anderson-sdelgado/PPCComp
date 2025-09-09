package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckSection {
    suspend operator fun invoke(
        nroSection: String
    ): Result<Boolean>
}

class ICheckSection @Inject constructor(
    private val sectionRepository: SectionRepository,
    private val osRepository: OSRepository
): CheckSection {

    override suspend fun invoke(
        nroSection: String
    ): Result<Boolean> {
        try {
            val resultCheckNro = sectionRepository.checkNro(nroSection.toInt())
            if(resultCheckNro.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultCheckNro.exceptionOrNull()!!
                )
            }
            val check = resultCheckNro.getOrNull()!!
            if(!check) return Result.success(false)
            val resultGetIdSection = sectionRepository.getIdByNro(nroSection.toInt())
            if(resultGetIdSection.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = resultGetIdSection.exceptionOrNull()!!
                )
            }
            val idSection = resultGetIdSection.getOrNull()!!
            val resultCheckIdSection = osRepository.checkIdSection(idSection)
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