package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.PlotRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckPlotTest {

    private val plotRepository = mock<PlotRepository>()
    private val analysisRepository = mock<AnalysisRepository>()
    private val sectionRepository = mock<SectionRepository>()
    private val usecase = ICheckPlot(
        plotRepository = plotRepository,
        analysisRepository = analysisRepository,
        sectionRepository = sectionRepository
    )

    @Test
    fun `Check return failure if nroPlot is invalid`() =
        runTest {
            val result = usecase("200a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPlot"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository getSectionHeader`() =
        runTest {
            whenever(
                analysisRepository.getSectionHeader()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.getSectionHeader",
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
                "ICheckPlot -> IAnalysisRepository.getSectionHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in SectionRepository getIdByCod`() =
        runTest {
            whenever(
                analysisRepository.getSectionHeader()
            ).thenReturn(
                Result.success(200)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                resultFailure(
                    "ISectionRepository.getIdByCod",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPlot -> ISectionRepository.getIdByCod"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in PlotRepository checkByNroPlotAndIdSection`() =
        runTest {
            whenever(
                analysisRepository.getSectionHeader()
            ).thenReturn(
                Result.success(200)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                plotRepository.checkByNroPlotAndIdSection(
                    2,
                    1
                )
            ).thenReturn(
                resultFailure(
                    "IPlotRepository.checkByNroPlotAndIdSection",
                    "-",
                    Exception()
                )
            )
            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPlot -> IPlotRepository.checkByNroPlotAndIdSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if function execute successfully and PlotRepository checkByNroPlotAndIdSection return false`() =
        runTest {
            whenever(
                analysisRepository.getSectionHeader()
            ).thenReturn(
                Result.success(200)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                plotRepository.checkByNroPlotAndIdSection(
                    2,
                    1
                )
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase("2")
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
    fun `Check return true if function execute successfully and PlotRepository checkByNroPlotAndIdSection return true`() =
        runTest {
            whenever(
                analysisRepository.getSectionHeader()
            ).thenReturn(
                Result.success(200)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                plotRepository.checkByNroPlotAndIdSection(
                    2,
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("2")
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