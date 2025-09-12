package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckHarvesterTest {

    private val harvesterRepository = mock<HarvesterRepository>()
    private val usecase = ICheckHarvester(
        harvesterRepository = harvesterRepository
    )

    @Test
    fun `Check return failure if regAuditor is incorrect`() =
        runTest {
            val result = usecase("19759a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckHarvester"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in HarvesterRepository check`() =
        runTest {
            whenever(
                harvesterRepository.checkNroHarvester(19759)
            ).thenReturn(
                resultFailure(
                    "IHarvesterRepository.check",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckHarvester -> IHarvesterRepository.check"
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
                harvesterRepository.checkNroHarvester(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("19759")
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