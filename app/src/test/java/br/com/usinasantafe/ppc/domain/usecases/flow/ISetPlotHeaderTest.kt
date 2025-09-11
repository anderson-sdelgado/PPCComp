package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetPlotHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetPlotHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if nroPlot is invalid`() =
        runTest {
            val result = usecase("200a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetPlotHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository setPlotHeader`() =
        runTest {
            whenever(
                analysisRepository.setPlotHeader(20)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setPlotHeader",
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
                "ISetPlotHeader -> IAnalysisRepository.setPlotHeader"
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
                analysisRepository.setPlotHeader(20)
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