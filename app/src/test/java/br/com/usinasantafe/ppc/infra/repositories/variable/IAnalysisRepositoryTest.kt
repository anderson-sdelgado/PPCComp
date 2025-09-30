package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.AnalysisRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.HeaderRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.variable.SampleRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.SampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.SampleRetrofitModelInput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.headerRoomModelToRetrofitModel
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.sampleRoomModelToRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.sharedPreferencesModelToRoomModel
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.whenever
import java.util.Date

class IAnalysisRepositoryTest {

    private val headerRoomDatasource = mock<HeaderRoomDatasource>()
    private val headerSharedPreferencesDatasource = mock<HeaderSharedPreferencesDatasource>()
    private val sampleRoomDatasource = mock<SampleRoomDatasource>()
    private val sampleSharedPreferencesDatasource = mock<SampleSharedPreferencesDatasource>()
    private val analysisRetrofitDatasource = mock<AnalysisRetrofitDatasource>()
    private val repository = IAnalysisRepository(
        headerRoomDatasource = headerRoomDatasource,
        headerSharedPreferencesDatasource = headerSharedPreferencesDatasource,
        sampleRoomDatasource = sampleRoomDatasource,
        sampleSharedPreferencesDatasource = sampleSharedPreferencesDatasource,
        analysisRetrofitDatasource = analysisRetrofitDatasource
    )

    @Test
    fun `listHeader - Check return failure if have error in HeaderRoomDatasource updateStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.updateStatus()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.updateStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.listHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.listHeader -> IHeaderRoomDatasource.updateStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listHeader - Check return failure if have error in HeaderRoomDatasource listByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.updateStatus()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.listByStatus(Status.CLOSE)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.listByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.listHeader()
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
                headerRoomDatasource.updateStatus()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.listByStatus(Status.CLOSE)
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
            val result = repository.listHeader()
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
    fun `setAuditorHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setAuditor`() =
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
            val result = repository.setAuditorHeader(
                pos = 1,
                regAuditor = 19759
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setAuditorHeader -> IHeaderSharedPreferencesDatasource.setAuditor"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setAuditorHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setAuditor(
                    pos = 1,
                    regAuditor = 19759
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setAuditorHeader(
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

    @Test
    fun `setDateHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setDate`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setDate(Date(1756928843000))
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setDate",
                    "-",
                    Exception()
                )
            )
            val result = repository.setDateHeader(Date(1756928843000))
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setDateHeader -> IHeaderSharedPreferencesDatasource.setDate"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setDateHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setDate(Date(1756928843000))
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setDateHeader(Date(1756928843000))
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
    fun `setTurnHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setTurn`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setTurn(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setTurn",
                    "-",
                    Exception()
                )
            )
            val result = repository.setTurnHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setTurnHeader -> IHeaderSharedPreferencesDatasource.setTurn"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setTurnHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setTurn(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setTurnHeader(1)
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
    fun `setOSHeader - Check return failure if have error in AnalysisDatasource setOS`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setOS(123456)
            ).thenReturn(
                resultFailure(
                    "IAnalysisDatasource.setOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.setOSHeader(123456)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setOSHeader -> IAnalysisDatasource.setOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getOSHeaderOpen - Check return failure if have error in HeaderSharedPreferencesDatasource getOS`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getOS()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.getOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getOSHeaderOpen()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.getOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getOSHeaderOpen - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getOS()
            ).thenReturn(
                Result.success(123456)
            )
            val result = repository.getOSHeaderOpen()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                123456
            )
        }

    @Test
    fun `setSectionHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setSection`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setSection(200)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setSection",
                    "-",
                    Exception()
                )
            )
            val result = repository.setSectionHeader(200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setSectionHeader -> IHeaderSharedPreferencesDatasource.setSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setSectionHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setSection(200)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSectionHeader(200)
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
    fun `getSectionHeader - Check return failure if have error in HeaderSharedPreferencesDatasource getSection`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getSection()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.getSection",
                    "-",
                    Exception()
                )
            )
            val result = repository.getSectionHeader()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.getSectionHeader -> IHeaderSharedPreferencesDatasource.getSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getSection - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.getSection()
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getSectionHeader()
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
    fun `setPlotHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setPlot`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setPlot(20)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setPlot",
                    "-",
                    Exception()
                )
            )
            val result = repository.setPlotHeader(20)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setPlotHeader -> IHeaderSharedPreferencesDatasource.setPlot"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setPlotHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setPlot(20)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setPlotHeader(20)
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
    fun `setFrontHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setFront`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setFront(3)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setFront",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFrontHeader(3)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setFrontHeader -> IHeaderSharedPreferencesDatasource.setFront"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setFrontHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setFront(3)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setFrontHeader(3)
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
    fun `setHarvesterHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setHarvester`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setHarvester(250)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setHarvester",
                    "-",
                    Exception()
                )
            )
            val result = repository.setHarvesterHeader(250)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setHarvesterHeader -> IHeaderSharedPreferencesDatasource.setHarvester"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setHarvesterHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setHarvester(200)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setHarvesterHeader(200)
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
    fun `setOperatorHeader - Check return failure if have error in HeaderSharedPreferencesDatasource setOperator`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setOperator(19759)
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.setOperator",
                    "-",
                    Exception()
                )
            )
            val result = repository.setOperatorHeader(19759)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setOperatorHeader -> IHeaderSharedPreferencesDatasource.setOperator"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setOperatorHeader - Check return failure if have error in HeaderSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                headerSharedPreferencesDatasource.setOperator(19759)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IHeaderSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.setOperatorHeader(19759)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setOperatorHeader -> IHeaderSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setOperatorHeader - Check return failure if have error in HeaderRoomDatasource save`() =
        runTest {
            val model = HeaderSharedPreferencesModel(
                regAuditor1 = 19759,
                date = Date(),
                nroTurn = 1,
                codSection = 1,
                nroPlot = 1,
                nroOS = 1,
                codFront = 1,
                nroHarvester = 1,
                regOperator = 19759
            )
            whenever(
                headerSharedPreferencesDatasource.setOperator(19759)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(model)
            )
            val modelCaptor = argumentCaptor<HeaderRoomModel>().apply {
                whenever(
                    headerRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    resultFailure(
                        "IHeaderRoomDatasource.save",
                        "-",
                        Exception()
                    )
                )
            }
            val result = repository.setOperatorHeader(19759)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setOperatorHeader -> IHeaderRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
            val modelRoom = modelCaptor.firstValue
            assertEquals(
                modelRoom.regAuditor1,
                19759
            )
            assertEquals(
                modelRoom.regAuditor2,
                null
            )
            assertEquals(
                modelRoom.regAuditor3,
                null
            )
            assertEquals(
                modelRoom.nroTurn,
                1
            )
            assertEquals(
                modelRoom.codSection,
                1
            )
            assertEquals(
                modelRoom.nroPlot,
                1
            )
            assertEquals(
                modelRoom.nroOS,
                1
            )
            assertEquals(
                modelRoom.codFront,
                1
            )
            assertEquals(
                modelRoom.nroHarvester,
                1
            )
            assertEquals(
                modelRoom.regOperator,
                19759
            )
            assertEquals(
                modelRoom.status,
                Status.CLOSE
            )
        }

    @Test
    fun `setOperatorHeader - Check return correct if function execute successfully`() =
        runTest {
            val model = HeaderSharedPreferencesModel(
                regAuditor1 = 19759,
                date = Date(),
                nroTurn = 1,
                codSection = 1,
                nroPlot = 1,
                nroOS = 1,
                codFront = 1,
                nroHarvester = 1,
                regOperator = 19759
            )
            whenever(
                headerSharedPreferencesDatasource.setOperator(19759)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(model)
            )
            val modelCaptor = argumentCaptor<HeaderRoomModel>().apply {
                whenever(
                    headerRoomDatasource.save(
                        capture()
                    )
                ).thenReturn(
                    Result.success(true)
                )
            }
            val result = repository.setOperatorHeader(19759)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val modelRoom = modelCaptor.firstValue
            assertEquals(
                modelRoom.regAuditor1,
                19759
            )
            assertEquals(
                modelRoom.regAuditor2,
                null
            )
            assertEquals(
                modelRoom.regAuditor3,
                null
            )
            assertEquals(
                modelRoom.nroTurn,
                1
            )
            assertEquals(
                modelRoom.codSection,
                1
            )
            assertEquals(
                modelRoom.nroPlot,
                1
            )
            assertEquals(
                modelRoom.nroOS,
                1
            )
            assertEquals(
                modelRoom.codFront,
                1
            )
            assertEquals(
                modelRoom.nroHarvester,
                1
            )
            assertEquals(
                modelRoom.regOperator,
                19759
            )
            assertEquals(
                modelRoom.status,
                Status.CLOSE
            )
        }

    @Test
    fun `setStatusHeaderById - Check return failure if have error in HeaderRoomDatasource setStatusById`() =
        runTest {
            whenever(
                headerRoomDatasource.setStatusById(
                    Status.OPEN,
                    1
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.setStatusById",
                    "-",
                    Exception()
                )
            )
            val result = repository.setStatusHeaderById(
                Status.OPEN,
                1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setStatusHeaderById -> IHeaderRoomDatasource.setStatusById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setStatusHeaderById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.setStatusById(
                    Status.OPEN,
                    1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setStatusHeaderById(
                Status.OPEN,
                1
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
    fun `getIdHeaderByStatus - Check return failure if have error in HeaderRoomDatasource getIdByStatus`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatus(Status.OPEN)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getIdByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.getIdHeaderByStatus(Status.OPEN)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.getIdHeaderByStatus -> IHeaderRoomDatasource.getIdByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getIdHeaderByStatus - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.getIdByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            val result = repository.getIdHeaderByStatus(Status.OPEN)
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
    fun `listSampleByIdHeader - Check return failure if have error in SampleRoomDatasource listByIdHeader`() =
        runTest {
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.listSampleByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.listSampleByIdHeader -> ISampleRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listSampleByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(
                    listOf(
                        SampleRoomModel(
                            id = 1,
                            idHeader = 1,
                            pos = 1,
                            tare = 1.0,
                            stalk = 1.0,
                            wholeCane = 1.0,
                            stump = 1.0,
                            piece = 1.0,
                            tip = 1.0,
                            slivers = 1.0,
                            stone = true,
                            treeStump = true,
                            weed = true,
                            anthill = true,
                            guineaGrass = false,
                            castorOilPlant = true,
                            signalGrass = true,
                            mucuna = true,
                            silkGrass = false
                        )
                    )
                )
            )
            val result = repository.listSampleByIdHeader(1)
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
                model.idHeader,
                1
            )
            assertEquals(
                model.tare!!,
                1.0,
                0.0
            )
            assertEquals(
                model.stalk!!,
                1.0,
                0.0
            )
            assertEquals(
                model.wholeCane!!,
                1.0,
                0.0
            )
            assertEquals(
                model.stump!!,
                1.0,
                0.0
            )
            assertEquals(
                model.piece!!,
                1.0,
                0.0
            )
            assertEquals(
                model.tip!!,
                1.0,
                0.0
            )
            assertEquals(
                model.slivers!!,
                1.0,
                0.0
            )
            assertEquals(
                model.stone,
                true
            )
            assertEquals(
                model.treeStump,
                true
            )
            assertEquals(
                model.weed,
                true
            )
            assertEquals(
                model.anthill,
                true
            )
            assertEquals(
                model.guineaGrass,
                false
            )
            assertEquals(
                model.castorOilPlant,
                true
            )
            assertEquals(
                model.signalGrass,
                true
            )
            assertEquals(
                model.mucuna,
                true
            )
            assertEquals(
                model.silkGrass,
                false
            )
        }

    @Test
    fun `deleteSampleByIdHeader - Check return failure if have error in SampleRoomDatasource deleteByIdHeader`() =
        runTest {
            whenever(
                sampleRoomDatasource.deleteByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.deleteByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteSampleByIdHeader(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.deleteSampleByIdHeader -> ISampleRoomDatasource.deleteByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteSampleByIdHeader - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleRoomDatasource.deleteByIdHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteSampleByIdHeader(1)
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
    fun `deleteHeaderById - Check return failure if have error in HeaderRoomDatasource deleteById`() =
        runTest {
            whenever(
                headerRoomDatasource.deleteById(1)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.deleteById",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteHeaderById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.deleteHeaderById -> IHeaderRoomDatasource.deleteById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteHeaderById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.deleteById(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteHeaderById(1)
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
    fun `deleteSampleById - Check return failure if have error in SampleRoomDatasource deleteById`() =
        runTest {
            whenever(
                sampleRoomDatasource.deleteById(1)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.deleteById",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteSampleById(1)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.deleteSampleById -> ISampleRoomDatasource.deleteById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteSampleById - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleRoomDatasource.deleteById(1)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteSampleById(1)
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
    fun `setFieldSample - Check return failure if have error in SampleSharedPreferencesDatasource clean`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "ISampleSharedPreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFieldSample(
                field = Field.TARE,
                value = 1.023
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setFieldSample -> ISampleSharedPreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setFieldSample - Check return failure if have error in SampleSharedPreferencesDatasource setValue and field is Tare`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sampleSharedPreferencesDatasource.setValue(
                    Field.TARE,
                    1.023
                )
            ).thenReturn(
                resultFailure(
                    "ISampleSharedPreferencesDatasource.setValue",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFieldSample(
                field = Field.TARE,
                value = 1.023
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setFieldSample -> ISampleSharedPreferencesDatasource.setValue"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setFieldSample - Check return correct if function execute successfully and field is Tare`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sampleSharedPreferencesDatasource.setValue(
                    Field.TARE,
                    1.023
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setFieldSample(
                field = Field.TARE,
                value = 1.023
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
    fun `setFieldSample - Check return failure if have error in SampleSharedPreferencesDatasource setValue and field is not Tare`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.setValue(
                    Field.TIP,
                    1.023
                )
            ).thenReturn(
                resultFailure(
                    "ISampleSharedPreferencesDatasource.setValue",
                    "-",
                    Exception()
                )
            )
            val result = repository.setFieldSample(
                field = Field.TIP,
                value = 1.023
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.setFieldSample -> ISampleSharedPreferencesDatasource.setValue"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setFieldSample - Check return correct if function execute successfully and field is not Tare`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sampleSharedPreferencesDatasource.setValue(
                    Field.TARE,
                    1.023
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setFieldSample(
                field = Field.TARE,
                value = 1.023
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
    fun `getTareSample - Check return failure if have error in AnalysisDatasource getTare`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.getTare()
            ).thenReturn(
                resultFailure(
                    "IAnalysisDatasource.getTare",
                    "-",
                    Exception()
                )
            )
            val result = repository.getTareSample()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.getTareSample -> IAnalysisDatasource.getTare"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getTareSample - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.getTare()
            ).thenReturn(
                Result.success(1.200)
            )
            val result = repository.getTareSample()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1.200,
                0.000
            )
        }

    @Test
    fun `setObsSample - Check return failure if have error in AnalysisDatasource setObs`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.setObs(
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisDatasource.setObsSample",
                    "-",
                    Exception()
                )
            )
            val result = repository.setObsSample(
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
                "IAnalysisRepository.setObsSample -> IAnalysisDatasource.setObsSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setObsSample - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.setObs(
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setObsSample(
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

    @Test
    fun `saveSample - Check return failure if have error in SampleSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "ISampleSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveSample()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.saveSample -> ISampleSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveSample - Check return failure if sample shared preferences is missing data`() =
        runTest {
            val modelSharedPreferencesModel = SampleSharedPreferencesModel(
                tare = null
            )
            whenever(
                sampleSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(modelSharedPreferencesModel)
            )
            val result = repository.saveSample()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.saveSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `saveSample - Check return failure if have error in HeaderRoomDatasource getIdByStatus`() =
        runTest {
            val sharedPreferencesModel = SampleSharedPreferencesModel(
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 2.0,
                stump = 2.0,
                piece = 2.0,
                tip = 2.0,
                slivers = 2.0,
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true,
                guineaGrass = false,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = false
            )
            whenever(
                sampleSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                headerRoomDatasource.getIdByStatus(Status.OPEN)
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.getIdByStatus",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveSample()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.saveSample -> IHeaderRoomDatasource.getIdByStatus"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveSample - Check return failure if have error in SampleRoomDatasource save`() =
        runTest {
            val sharedPreferencesModel = SampleSharedPreferencesModel(
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 2.0,
                stump = 2.0,
                piece = 2.0,
                tip = 2.0,
                slivers = 2.0,
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true,
                guineaGrass = false,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = false
            )
            whenever(
                sampleSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                headerRoomDatasource.getIdByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            val roomModel = sharedPreferencesModel.sharedPreferencesModelToRoomModel(1)
            whenever(
                sampleRoomDatasource.save(roomModel)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.saveSample()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.saveSample -> ISampleRoomDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `saveSample - Check return correct if function execute successfully`() =
        runTest {
            val sharedPreferencesModel = SampleSharedPreferencesModel(
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 2.0,
                stump = 2.0,
                piece = 2.0,
                tip = 2.0,
                slivers = 2.0,
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true,
                guineaGrass = false,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = false
            )
            whenever(
                sampleSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(sharedPreferencesModel)
            )
            whenever(
                headerRoomDatasource.getIdByStatus(Status.OPEN)
            ).thenReturn(
                Result.success(1)
            )
            val roomModel = sharedPreferencesModel.sharedPreferencesModelToRoomModel(1)
            whenever(
                sampleRoomDatasource.save(roomModel)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.saveSample()
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
    fun `setSubObsSample - Check return failure if have error in SampleSharedPreferencesDatasource setSubObs`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.setSubObs(
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            ).thenReturn(
                resultFailure(
                    "ISampleSharedPreferencesDatasource.setSubObs",
                    "-",
                    Exception()
                )
            )
            val result = repository.setSubObsSample(
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
                "IAnalysisRepository.setSubObsSample -> ISampleSharedPreferencesDatasource.setSubObs"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `setSubObsSample - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sampleSharedPreferencesDatasource.setSubObs(
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.setSubObsSample(
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

    @Test
    fun `checkSend - Check return failure if have error in HeaderRoomDatasource checkSend`() =
        runTest {
            whenever(
                headerRoomDatasource.checkSend()
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.checkSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkSend()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.checkSend -> IHeaderRoomDatasource.checkSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkSend - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                headerRoomDatasource.checkSend()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkSend()
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
    fun `send - Check return failure if have error in HeaderRoomDatasource listByStatusSend`() =
        runTest {
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.listByStatusSend",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.send -> IHeaderRoomDatasource.listByStatusSend"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in SampleRoomDatasource listByIdHeader`() =
        runTest {
            val headerRoomModelList =
                listOf(
                    HeaderRoomModel(
                        id = 1,
                        regAuditor1 = 1,
                        date = Date(),
                        nroTurn = 1,
                        codSection = 1,
                        nroPlot = 1,
                        nroOS = 1,
                        codFront = 1,
                        nroHarvester = 1,
                        regOperator = 1,
                        status = Status.FINISH,
                        statusSend = StatusSend.SEND
                    )
                )
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(headerRoomModelList)
            )
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.listByIdHeader",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.send -> ISampleRoomDatasource.listByIdHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in AnalysisRetrofitDatasource send`() =
        runTest {
            val headerRoomModelList =
                listOf(
                    HeaderRoomModel(
                        id = 1,
                        regAuditor1 = 1,
                        date = Date(),
                        nroTurn = 1,
                        codSection = 1,
                        nroPlot = 1,
                        nroOS = 1,
                        codFront = 1,
                        nroHarvester = 1,
                        regOperator = 1,
                        status = Status.FINISH,
                        statusSend = StatusSend.SEND
                    )
                )
            val sampleRoomModelList =
                listOf(
                    SampleRoomModel(
                        id = 1,
                        idHeader = 1,
                        pos = 1,
                        tare = 1.0,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        stone = true,
                        treeStump = true,
                        weed = true,
                        anthill = true,
                        guineaGrass = true,
                        castorOilPlant = true,
                        signalGrass = true,
                        mucuna = true,
                        silkGrass = true
                    )
                )
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(headerRoomModelList)
            )
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(sampleRoomModelList)
            )
            val retrofitModelOutputList = headerRoomModelList.map { headerRoomModel ->
                headerRoomModel.headerRoomModelToRetrofitModel(
                    number = 16997417840,
                    sampleList = sampleRoomModelList.map { it.sampleRoomModelToRetrofitModel() }
                )
            }
            whenever(
                analysisRetrofitDatasource.send(
                    token = "token",
                    retrofitModelOutputList = retrofitModelOutputList
                )
            ).thenReturn(
                resultFailure(
                    "IAnalysisRetrofitDatasource.send",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.send -> IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in SampleRoomDatasource setIdServById`() =
        runTest {
            val headerRoomModelList =
                listOf(
                    HeaderRoomModel(
                        id = 1,
                        regAuditor1 = 1,
                        date = Date(),
                        nroTurn = 1,
                        codSection = 1,
                        nroPlot = 1,
                        nroOS = 1,
                        codFront = 1,
                        nroHarvester = 1,
                        regOperator = 1,
                        status = Status.FINISH,
                        statusSend = StatusSend.SEND
                    )
                )
            val sampleRoomModelList =
                listOf(
                    SampleRoomModel(
                        id = 1,
                        idHeader = 1,
                        pos = 1,
                        tare = 1.0,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        stone = true,
                        treeStump = true,
                        weed = true,
                        anthill = true,
                        guineaGrass = true,
                        castorOilPlant = true,
                        signalGrass = true,
                        mucuna = true,
                        silkGrass = true
                    )
                )
            val headerRetrofitModelInputList =
                listOf(
                    HeaderRetrofitModelInput(
                        id = 1,
                        idServ = 1,
                        sampleList = listOf(
                            SampleRetrofitModelInput(
                                id = 1,
                                idServ = 1
                            )
                        )
                    )
                )
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(headerRoomModelList)
            )
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(sampleRoomModelList)
            )
            val retrofitModelOutputList = headerRoomModelList.map { headerRoomModel ->
                headerRoomModel.headerRoomModelToRetrofitModel(
                    number = 16997417840,
                    sampleList = sampleRoomModelList.map { it.sampleRoomModelToRetrofitModel() }
                )
            }
            whenever(
                analysisRetrofitDatasource.send(
                    token = "token",
                    retrofitModelOutputList = retrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerRetrofitModelInputList)
            )
            whenever(
                sampleRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                resultFailure(
                    "ISampleRoomDatasource.setIdServById",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.send -> ISampleRoomDatasource.setIdServById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return failure if have error in HeaderRoomDatasource setIdServAndSentById`() =
        runTest {
            val headerRoomModelList =
                listOf(
                    HeaderRoomModel(
                        id = 1,
                        regAuditor1 = 1,
                        date = Date(),
                        nroTurn = 1,
                        codSection = 1,
                        nroPlot = 1,
                        nroOS = 1,
                        codFront = 1,
                        nroHarvester = 1,
                        regOperator = 1,
                        status = Status.FINISH,
                        statusSend = StatusSend.SEND
                    )
                )
            val sampleRoomModelList =
                listOf(
                    SampleRoomModel(
                        id = 1,
                        idHeader = 1,
                        pos = 1,
                        tare = 1.0,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        stone = true,
                        treeStump = true,
                        weed = true,
                        anthill = true,
                        guineaGrass = true,
                        castorOilPlant = true,
                        signalGrass = true,
                        mucuna = true,
                        silkGrass = true
                    )
                )
            val headerRetrofitModelInputList =
                listOf(
                    HeaderRetrofitModelInput(
                        id = 1,
                        idServ = 1,
                        sampleList = listOf(
                            SampleRetrofitModelInput(
                                id = 1,
                                idServ = 1
                            )
                        )
                    )
                )
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(headerRoomModelList)
            )
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(sampleRoomModelList)
            )
            val retrofitModelOutputList = headerRoomModelList.map { headerRoomModel ->
                headerRoomModel.headerRoomModelToRetrofitModel(
                    number = 16997417840,
                    sampleList = sampleRoomModelList.map { it.sampleRoomModelToRetrofitModel() }
                )
            }
            whenever(
                analysisRetrofitDatasource.send(
                    token = "token",
                    retrofitModelOutputList = retrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerRetrofitModelInputList)
            )
            whenever(
                sampleRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.setIdServAndSentById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                resultFailure(
                    "IHeaderRoomDatasource.setIdServAndSentById",
                    "-",
                    Exception()
                )
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRepository.send -> IHeaderRoomDatasource.setIdServAndSentById"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {
            val headerRoomModelList =
                listOf(
                    HeaderRoomModel(
                        id = 1,
                        regAuditor1 = 1,
                        date = Date(),
                        nroTurn = 1,
                        codSection = 1,
                        nroPlot = 1,
                        nroOS = 1,
                        codFront = 1,
                        nroHarvester = 1,
                        regOperator = 1,
                        status = Status.FINISH,
                        statusSend = StatusSend.SEND
                    )
                )
            val sampleRoomModelList =
                listOf(
                    SampleRoomModel(
                        id = 1,
                        idHeader = 1,
                        pos = 1,
                        tare = 1.0,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        stone = true,
                        treeStump = true,
                        weed = true,
                        anthill = true,
                        guineaGrass = true,
                        castorOilPlant = true,
                        signalGrass = true,
                        mucuna = true,
                        silkGrass = true
                    )
                )
            val headerRetrofitModelInputList =
                listOf(
                    HeaderRetrofitModelInput(
                        id = 1,
                        idServ = 1,
                        sampleList = listOf(
                            SampleRetrofitModelInput(
                                id = 1,
                                idServ = 1
                            )
                        )
                    )
                )
            whenever(
                headerRoomDatasource.listByStatusSend(
                    StatusSend.SEND
                )
            ).thenReturn(
                Result.success(headerRoomModelList)
            )
            whenever(
                sampleRoomDatasource.listByIdHeader(1)
            ).thenReturn(
                Result.success(sampleRoomModelList)
            )
            val retrofitModelOutputList = headerRoomModelList.map { headerRoomModel ->
                headerRoomModel.headerRoomModelToRetrofitModel(
                    number = 16997417840,
                    sampleList = sampleRoomModelList.map { it.sampleRoomModelToRetrofitModel() }
                )
            }
            whenever(
                analysisRetrofitDatasource.send(
                    token = "token",
                    retrofitModelOutputList = retrofitModelOutputList
                )
            ).thenReturn(
                Result.success(headerRetrofitModelInputList)
            )
            whenever(
                sampleRoomDatasource.setIdServById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                headerRoomDatasource.setIdServAndSentById(
                    id = 1,
                    idServ = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.send(
                token = "token",
                number = 16997417840
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