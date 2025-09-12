package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetFrontHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetFrontHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if nroFront is incorrect`() =
        runTest {
            val result = usecase("3a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetFrontHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"3a\""
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository setFrontHeader`() =
        runTest {
            whenever(
                analysisRepository.setFrontHeader(3)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setFrontHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("3")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetFrontHeader -> IAnalysisRepository.setFrontHeader"
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
                analysisRepository.setFrontHeader(3)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("3")
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