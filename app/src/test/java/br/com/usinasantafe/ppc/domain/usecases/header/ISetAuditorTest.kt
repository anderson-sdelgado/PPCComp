package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetAuditorTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetAuditorHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if regAuditor is incorrect`() =
        runTest {
            val result = usecase(
                pos = 1,
                regAuditor = "19759a"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetAuditor"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }
    
    @Test
    fun `Check return failure if have error in AnalysisRepository setAuditor`() =
        runTest {
            whenever(
                analysisRepository.setAuditorHeader(
                    pos = 1,
                    regAuditor = 19759
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setAuditor",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                pos = 1,
                regAuditor = "19759"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetAuditor -> IAnalysisRepository.setAuditor"
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
                analysisRepository.setAuditorHeader(
                    pos = 1,
                    regAuditor = 19759
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                pos = 1,
                regAuditor = "19759"
            )
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