package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetSubObsSampleTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetSubObsSample(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository setSubObsSample`() =
        runTest {
            whenever(
                analysisRepository.setSubObsSample(
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setSubObsSample",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = true
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSubObsSample -> IAnalysisRepository.setSubObsSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository saveSample`() =
        runTest {
            whenever(
                analysisRepository.setSubObsSample(
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                analysisRepository.saveSample()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.saveSample",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = true
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSubObsSample -> IAnalysisRepository.saveSample"
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
                analysisRepository.setSubObsSample(
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                analysisRepository.saveSample()
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = true
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