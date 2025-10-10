package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.ConfigRepository
import br.com.usinasantafe.ppc.utils.CheckNetwork
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckUpdateAppTest {

    private val configRepository = mock<ConfigRepository>()
    private val checkNetwork = mock<CheckNetwork>()
    private val usecase = ICheckUpdateApp(
        configRepository = configRepository,
        checkNetwork = checkNetwork
    )

    @Test
    fun `Check return false if not have connection`() =
        runTest {
            whenever(
                checkNetwork()
            ).thenReturn(
                false
            )
            val result = usecase("2.00")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return failure if have error in ConfigRepository checkUpdateApp`() =
        runTest {
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                configRepository.checkUpdateApp("2.00")
            ).thenReturn(
                resultFailure(
                    "IConfigRepository.checkUpdateApp",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2.00")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckUpdateApp -> IConfigRepository.checkUpdateApp"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                configRepository.checkUpdateApp("2.00")
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("2.00")
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