package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test

class ISetDateHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetDateHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository setDateHeader`() =
        runTest {
            whenever(
                analysisRepository.setDateHeader(Date(1756928843000))
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setDateHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase(Date(1756928843000))
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetDateHeader -> IAnalysisRepository.setDateHeader"
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
                analysisRepository.setDateHeader(Date(1756928843000))
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(Date(1756928843000))
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