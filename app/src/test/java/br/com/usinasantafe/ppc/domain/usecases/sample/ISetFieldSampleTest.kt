package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Field
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ISetFieldSampleTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetFieldSample(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if field is incorrect`() =
        runTest {
            val result = usecase(
                field = Field.TARE,
                value = "1,02,0s"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetFieldSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: multiple points"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository setFieldSample`() =
        runTest {
            whenever(
                analysisRepository.setFieldSample(
                    field = Field.TARE,
                    value = 1.020
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setFieldSample",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                field = Field.TARE,
                value = "1,020"
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetFieldSample -> IAnalysisRepository.setFieldSample"
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
                analysisRepository.setFieldSample(
                    field = Field.TARE,
                    value = 1.020
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                field = Field.TARE,
                value = "1,020"
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