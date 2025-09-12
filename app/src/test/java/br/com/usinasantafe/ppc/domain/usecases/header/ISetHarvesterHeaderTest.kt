package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetHarvesterHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetHarvesterHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if nroHarvester is invalid`() =
        runTest {
            val result = usecase("200a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHarvesterHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository setHarvesterHeader`() =
        runTest {
            whenever(
                analysisRepository.setHarvesterHeader(20)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setHarvesterHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("20")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetHarvesterHeader -> IAnalysisRepository.setHarvesterHeader"
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
                analysisRepository.setHarvesterHeader(20)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("20")
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