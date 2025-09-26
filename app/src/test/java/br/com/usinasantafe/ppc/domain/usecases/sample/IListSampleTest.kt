package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.domain.entities.variable.Sample
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import br.com.usinasantafe.ppc.utils.Status
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

class IListSampleTest {

    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = IListSample(
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
                "IListSample -> IAnalysisRepository.getIdHeaderByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository listSampleByIdHeader`() =
        runTest {
            whenever(
                analysisRepository.getIdHeaderByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.listSampleByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.listSampleByIdHeader",
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
                "IListSample -> IAnalysisRepository.listSampleByIdHeader"
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
                analysisRepository.listSampleByIdHeader(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        Sample(
                            id = 1,
                            idHeader = 1,
                            tare = 1.0,
                            stalk = 1.0,
                            wholeCane = 1.0,
                            stump = 1.0,
                            piece = 1.0,
                            tip = 1.0,
                            slivers = 1.0,
                            stone = true,
                            treeStump = true,
                            anthill = true,
                            weed = true,
                            guineaGrass = false,
                            castorOilPlant = true,
                            signalGrass = true,
                            mucuna = true,
                            silkGrass = false
                        )
                    )
                )
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
                model.stalk,
                1.0
            )
            assertEquals(
                model.wholeCane,
                1.0
            )
            assertEquals(
                model.stump,
                1.0
            )
            assertEquals(
                model.piece,
                1.0
            )
            assertEquals(
                model.tip,
                1.0
            )
            assertEquals(
                model.slivers,
                1.0
            )
            assertEquals(
                model.obs,
                "PEDRA - TOCO DE ARVORE - FORMIGUEIROS - PLANTA DANINHA - MAMONA - CAPIM-BRAQUIÁRIA - MUCUNA"
            )
        }

}