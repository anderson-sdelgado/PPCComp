package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class ISetObsSampleTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ISetObsSample(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository setObsSample`() =
        runTest {
            whenever(
                analysisRepository.setObsSample(
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.setObsSample",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetObsSample -> IAnalysisRepository.setObsSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and weed is false`() =
        runTest {
            whenever(
                analysisRepository.setObsSample(
                    stone = true,
                    treeStump = true,
                    weed = false,
                    anthill = true
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                stone = true,
                treeStump = true,
                weed = false,
                anthill = true
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

    @Test
    fun `Check return failure if have error in AnalysisRepository saveSample and weed is true`() =
        runTest {
            whenever(
                analysisRepository.setObsSample(
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true
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
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetObsSample -> IAnalysisRepository.saveSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully and weed is true`() =
        runTest {
            whenever(
                analysisRepository.setObsSample(
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true
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
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true
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