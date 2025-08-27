package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Plot
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.PlotRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.PlotRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.PlotRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IPlotRepositoryTest {
    
    private val plotRoomDatasource = mock<PlotRoomDatasource>()
    private val plotRetrofitDatasource = mock<PlotRetrofitDatasource>()
    private val repository = IPlotRepository(
        plotRetrofitDatasource = plotRetrofitDatasource,
        plotRoomDatasource = plotRoomDatasource
    )
    
    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                PlotRoomModel(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                )
            )
            val entityList = listOf(
                Plot(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                )
            )
            whenever(
                plotRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IPlotRoomDatasource.addAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlotRepository.addAll -> IPlotRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                PlotRoomModel(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                )
            )
            val entityList = listOf(
                Plot(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                )
            )
            whenever(
                plotRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
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
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                plotRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IPlotRoomDatasource.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlotRepository.deleteAll -> IPlotRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                plotRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
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
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                plotRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IPlotRetrofitDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                PlotRetrofitModel(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                ),
                PlotRetrofitModel(
                    idPlot = 2,
                    codPlot = 20,
                    idSection = 2
                )
            )
            val entityList = listOf(
                Plot(
                    idPlot = 1,
                    codPlot = 10,
                    idSection = 1
                ),
                Plot(
                    idPlot = 2,
                    codPlot = 20,
                    idSection = 2
                )
            )
            whenever(
                plotRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

}