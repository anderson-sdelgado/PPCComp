package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetSectionHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetSectionHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if codSection is incorrect`() =
        runTest {
            val result = usecase("200a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSectionHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository setSectionHeader`() =
        runTest {
            whenever(
                analysisRepository.setSectionHeader(200)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setSectionHeader",
                    "-",
                    Exception()
                )
            )
            val result = usecase("200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSectionHeader -> IAnalysisRepository.setSectionHeader"
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
                analysisRepository.setSectionHeader(
                    codSection = 200
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                codSection = "200"
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