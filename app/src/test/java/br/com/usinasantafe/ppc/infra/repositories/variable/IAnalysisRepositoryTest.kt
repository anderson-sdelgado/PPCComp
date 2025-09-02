package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test

class IAnalysisRepositoryTest {

    private val headerRoomDatasource = mock<HeaderRoomDatasource>()
    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val sampleRoomDatasource = mock<SampleRoomDatasource>()
    private val repository = IAnalysisRepository(
        headerRoomDatasource = headerRoomDatasource,
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource,
        sampleRoomDatasource = sampleRoomDatasource
    )

    @Test
    fun `listHeader - Check return failure if have error in HeaderRoomDatasource listByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.listByStatus(Status.OPEN)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.listByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.listHeaderByStatusOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.listHeader -> IHeaderRoomDatasource.listByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.listByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderRoomModel(
                            id = 1,
                            regAuditor1 = 1,
                            regAuditor2 = 2,
                            regAuditor3 = 3,
                            date = Date(),
                            nroTurn = 1,
                            codSection = 1,
                            nroPlot = 1,
                            nroOS = 1,
                            codFront = 1,
                            nroHarvester = 1,
                            regOperator = 1,
                        )
                    )
                )
            )
            val result = repository.listHeaderByStatusOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.id,
                1
            )
            assertEquals(
                entity.regAuditor1,
                1L
            )
            assertEquals(
                entity.regAuditor2,
                2L
            )
            assertEquals(
                entity.regAuditor3,
                3L
            )
            assertEquals(
                entity.nroTurn,
                1
            )
            assertEquals(
                entity.codSection,
                1
            )
            assertEquals(
                entity.nroPlot,
                1
            )
            assertEquals(
                entity.nroOS,
                1
            )
            assertEquals(
                entity.codFront,
                1
            )
            assertEquals(
                entity.nroHarvester,
                1
            )
            assertEquals(
                entity.regOperator,
                1L
            )
        }

    @Test
    fun `countSampleByIdHeader - Check return failure if have error in SampleRoomDatasource countByIdHeader`() =
        runTest {
            whenever(
                sampleRoomDatasource.countByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.countByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.countSampleByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.countSampleByIdHeader -> ISampleRoomDatasource.countByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `countSampleByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleRoomDatasource.countByIdHeader(1)
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.countSampleByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }
    
    @Test
    fun `setAuditor - Check return failure if have error in HeaderSharedPreferencesDatasource setAuditor`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setAuditor(
                    pos = 1,
                    regAuditor = 19759
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setAuditor",
                    "-",
                    Exception()
                )
            )
            val result = repository.setAuditor(
                pos = 1,
                regAuditor = 19759
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setAuditor -> IHeaderSharedPreferencesDatasource.setAuditor"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setAuditor - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setAuditor(
                    pos = 1,
                    regAuditor = 19759
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setAuditor(
                pos = 1,
                regAuditor = 19759
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