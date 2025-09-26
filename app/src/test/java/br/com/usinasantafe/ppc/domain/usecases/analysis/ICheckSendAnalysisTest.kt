package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckSendAnalysisTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ICheckSendAnalysis(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository checkSend`() =
        runTest {
            whenever(
                analysisRepository.checkSend()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.checkSend",
                    "-",
                    Exception()
                )
            )
            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckSendAnalysis -> IAnalysisRepository.checkSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

}