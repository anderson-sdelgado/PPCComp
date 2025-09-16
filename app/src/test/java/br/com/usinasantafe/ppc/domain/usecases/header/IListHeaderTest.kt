package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.entities.variable.Header
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IListHeaderTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = IListHeader(
        analysisRepository = analysisRepository
    )

    @Test
    fun `Check return failure if have error in AnalysisRepository listHeader`() =
        runTest {
            whenever(
                analysisRepository.listHeader()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.listHeader",
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
                "IListHeader -> IAnalysisRepository.listHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository countSampleByIdHeader`() =
        runTest {
            whenever(
                analysisRepository.listHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                        )
                    )
                )
            )
            whenever(
                analysisRepository.countSampleByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.countSampleByIdHeader",
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
                "IListHeader -> IAnalysisRepository.countSampleByIdHeader"
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
                analysisRepository.listHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        Header(
                            id = 1,
                            nroHarvester = 1,
                            nroTurn = 1,
                            regOperator = 1,
                            codFront = 1
                        )
                    )
                )
            )
            whenever(
                analysisRepository.countSampleByIdHeader(1)
            ).thenReturn(
                Result.success(0)
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.id,
                1
            )
            assertEquals(
                model.harvester,
                1
            )
            assertEquals(
                model.operator,
                1
            )
            assertEquals(
                model.front,
                1
            )
            assertEquals(
                model.turn,
                1
            )
            assertEquals(
                model.qtdSample,
                0
            )
        }

}