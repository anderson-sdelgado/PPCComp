package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ICheckWeightRelationTareTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ICheckWeightRelationTare(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository getTareSample`() =
        runTest {
            whenever(
                analysisRepository.getTareSample()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.getTareSample",
                    "-",
                    Exception()
                )
            )
            val result = usecase("1,020")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckWeightRelationTare -> IAnalysisRepository.getTareSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if value digit is not valid`() =
        runTest {
            whenever(
                analysisRepository.getTareSample()
            ).thenReturn(
                Result.success(1.000)
            )
            val result = usecase("1,020a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckWeightRelationTare"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"1.020a\""
            )
        }

    @Test
    fun `Check return false if the entered value is less than the tare`() =
        runTest {
            whenever(
                analysisRepository.getTareSample()
            ).thenReturn(
                Result.success(1.500)
            )
            val result = usecase("1,020")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `Check return true if the entered value is greater than or equal to the tare`() =
        runTest {
            whenever(
                analysisRepository.getTareSample()
            ).thenReturn(
                Result.success(1.000)
            )
            val result = usecase("1,020")
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