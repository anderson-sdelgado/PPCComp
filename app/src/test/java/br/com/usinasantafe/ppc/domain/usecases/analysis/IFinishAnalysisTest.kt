package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IFinishAnalysisTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = IFinishAnalysis(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository getIdHeaderByStatus`() =
        runTest {
            whenever(
                analysisRepository.getIdHeaderByStatus(Status.OPEN)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.getIdHeaderByStatus",
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
                "IFinishAnalysis -> IAnalysisRepository.getIdHeaderByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository finishByIdHeader`() =
        runTest {
            whenever(
                analysisRepository.getIdHeaderByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.setStatusHeaderById(
                    Status.FINISH,
                    1
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setStatusHeaderById",
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
                "IFinishAnalysis -> IAnalysisRepository.setStatusHeaderById"
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
                analysisRepository.getIdHeaderByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.setStatusHeaderById(
                    Status.FINISH,
                    1
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