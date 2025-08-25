package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.FlagUpdate
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ISetFinishUpdateAllTableTest {

    private val configRepository = mock<ConfigRepository>()
    private val usecase = ISetFinishUpdateAllTable(
        configRepository
    )

    @Test
    fun `Check return failure if have error in ConfigRepository setFlagUpdate`() =
        runTest {
            whenever(
                configRepository.setFlagUpdate(
                    FlagUpdate.UPDATED
                )
            ).thenReturn(
                resultFailure(
                    context = "IConfigRepository.setFlagUpdate",
                    message = "-",
                    cause = Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetFinishUpdateAllTable -> IConfigRepository.setFlagUpdate"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if usecase is success`() =
        runTest {
            whenever(
                configRepository.setFlagUpdate(
                    FlagUpdate.UPDATED
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}