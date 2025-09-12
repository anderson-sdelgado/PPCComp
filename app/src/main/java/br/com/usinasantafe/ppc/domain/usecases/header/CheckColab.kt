package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

interface CheckColab {
    suspend operator fun invoke(regColab: String): Result<Boolean>
}

class ICheckColab @Inject constructor(
    private val colabRepository: ColabRepository
): CheckColab {

    override suspend fun invoke(regColab: String): Result<Boolean> {
        try {
            val result = colabRepository.checkRegColab(regColab.toInt())
            if(result.isFailure){
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

}