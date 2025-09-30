package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals

@HiltAndroidTest
class ISendAnalysisTest {


    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SendAnalysis

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Test
    fun check_return_failure_if_not_have_data_in_config_internal() =
        runTest {

            hiltRule.inject()

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendAnalysis -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_web_service_errors_connection() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendAnalysis -> IAnalysisRepository.send -> IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )
        }

    @Test
    fun check_return_failure_if_have_error_404() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendAnalysis -> IAnalysisRepository.send -> IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_have_failure_connection() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendAnalysis -> IAnalysisRepository.send -> IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
        }

    @Test
    fun check_return_failure_if_sent_data_incorrect() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"id":1a}"""))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISendAnalysis -> IAnalysisRepository.send -> IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
        }

    @Test
    fun check_return_true_if_process_execute_successfully_and_send_empty_and_return_empty() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""[]"""))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            val result = usecase()
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
    fun check_return_true_if_process_execute_successfully() =
        runTest {
            val response = """
                [
                    {
                        "id": 1,
                        "idServ": 10000,
                        "sampleList": [
                            {
                                "id": 1,
                                "idServ": 120000
                            }
                        ]
                    }
                ]
            """.trimIndent()
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(response))
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(2)

            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val headerRoomModelList = headerDao.all()
            assertEquals(
                headerRoomModelList.size,
                1
            )
            val headerRoomModel = headerRoomModelList.first()
            assertEquals(
                headerRoomModel.id,
                1
            )
            assertEquals(
                headerRoomModel.idServ,
                10000
            )
            assertEquals(
                headerRoomModel.regAuditor1,
                19759
            )
            assertEquals(
                headerRoomModel.regAuditor2,
                null
            )
            assertEquals(
                headerRoomModel.regAuditor3,
                null
            )
            assertEquals(
                headerRoomModel.nroTurn,
                1
            )
            assertEquals(
                headerRoomModel.codSection,
                1
            )
            assertEquals(
                headerRoomModel.nroPlot,
                1
            )
            assertEquals(
                headerRoomModel.nroOS,
                1
            )
            assertEquals(
                headerRoomModel.codFront,
                1
            )
            assertEquals(
                headerRoomModel.nroHarvester,
                1
            )
            assertEquals(
                headerRoomModel.regOperator,
                18017
            )
            val sampleRoomModelList = sampleDao.all()
            assertEquals(
                sampleRoomModelList.size,
                1
            )
            val sampleRoomModel = sampleRoomModelList.first()
            assertEquals(
                sampleRoomModel.id,
                1
            )
            assertEquals(
                sampleRoomModel.idServ,
                120000
            )
            assertEquals(
                sampleRoomModel.idHeader,
                1
            )
            assertEquals(
                sampleRoomModel.pos,
                1
            )
            assertEquals(
                sampleRoomModel.tare,
                1.0
            )
            assertEquals(
                sampleRoomModel.stalk,
                1.0
            )
            assertEquals(
                sampleRoomModel.wholeCane,
                1.0
            )
            assertEquals(
                sampleRoomModel.stump,
                1.0
            )
            assertEquals(
                sampleRoomModel.piece,
                1.0
            )
            assertEquals(
                sampleRoomModel.tip,
                1.0
            )
            assertEquals(
                sampleRoomModel.slivers,
                1.0
            )
            assertEquals(
                sampleRoomModel.stone,
                true
            )
            assertEquals(
                sampleRoomModel.treeStump,
                true
            )
            assertEquals(
                sampleRoomModel.weed,
                true
            )
            assertEquals(
                sampleRoomModel.anthill,
                true
            )
            assertEquals(
                sampleRoomModel.guineaGrass,
                true
            )
            assertEquals(
                sampleRoomModel.castorOilPlant,
                true
            )
            assertEquals(
                sampleRoomModel.signalGrass,
                true
            )
            assertEquals(
                sampleRoomModel.mucuna,
                true
            )
            assertEquals(
                sampleRoomModel.silkGrass,
                true
            )
        }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                idServ = 1,
                number = 16997417840,
                version = "1.0",
                password = "12345",
            )
        )

        if (level == 1) return

        headerDao.insert(
            HeaderRoomModel(
                id = 1,
                regAuditor1 = 19759,
                nroTurn = 1,
                codSection = 1,
                nroPlot = 1,
                nroOS = 1,
                codFront = 1,
                nroHarvester = 1,
                regOperator = 18017,
                date = Date(),
                status = Status.FINISH,
                statusSend = StatusSend.SEND,
            )
        )

        sampleDao.insert(
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
                silkGrass = true,
            )
        )

        if (level == 2) return

    }
}