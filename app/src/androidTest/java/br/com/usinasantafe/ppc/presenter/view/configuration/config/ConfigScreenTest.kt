package br.com.usinasantafe.ppc.presenter.view.configuration.config

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.WEB_ALL_COLAB
import br.com.usinasantafe.ppc.utils.WEB_ALL_HARVESTER
import br.com.usinasantafe.ppc.utils.WEB_ALL_PLOT
import br.com.usinasantafe.ppc.utils.WEB_ALL_SECTION
import br.com.usinasantafe.ppc.utils.WEB_SAVE_TOKEN
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ConfigScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var harvesterDao: HarvesterDao

    @Inject
    lateinit var plotDao: PlotDao

    @Inject
    lateinit var sectionDao: SectionDao

    private val resultTokenFailure = """{"idServ":1a}"""

    private val resultToken = """{"idServ":1}"""

    private val resultColabListFailure = """
        [
          {"regColab":1a},
          {"regColab":2}
        ]
    """.trimIndent()

    private val resultColabListRepeated = """
        [
          {"regColab":1},
          {"regColab":1}
        ]
    """.trimIndent()

    private val resultColabList = """
        [
          {"regColab":1},
          {"regColab":2}
        ]
    """.trimIndent()

    private val resultHarvesterListFailure = """
        [
          {"nroHarvester":1a},
          {"nroHarvester":2}
        ]
    """.trimIndent()

    private val resultHarvesterListRepeated = """
        [
          {"nroHarvester":1},
          {"nroHarvester":1}
        ]
    """.trimIndent()

    private val resultHarvesterList = """
        [
          {"nroHarvester":1},
          {"nroHarvester":2}
        ]
    """.trimIndent()

    private val resultPlotListFailure = """
        [
          {"idPlot":1s,"codPlot":1,"idSection":1},
          {"idPlot":2,"codPlot":2,"idSection":1}
        ]
    """.trimIndent()

    private val resultPlotListRepeated = """
        [
          {"idPlot":1,"codPlot":1,"idSection":1},
          {"idPlot":1,"codPlot":1,"idSection":1}
        ]
    """.trimIndent()

    private val resultPlotList = """
        [
          {"idPlot":1,"codPlot":1,"idSection":1},
          {"idPlot":2,"codPlot":2,"idSection":1}
        ]
    """.trimIndent()

    private val resultSectionListFailure = """
        [
          {"idSection":1s,"codSection":1},
          {"idSection":2,"codSection":2}
        ]
    """.trimIndent()

    private val resultSectionListRepeated = """
        [
          {"idSection":1,"codSection":1},
          {"idSection":1,"codSection":1}
        ]
    """.trimIndent()

    private val resultSectionList = """
        [
          {"idSection":1,"codSection":1},
          {"idSection":2,"codSection":2}
        ]
    """.trimIndent()

    private val dispatcherTokenFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultTokenFailure)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody("")
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherToken: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody("")
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody("")
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColabFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabListFailure)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody("")
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColabRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabListRepeated)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody("")
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherColab: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody("")
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherHarvesterFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterListFailure)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherHarvesterRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterListRepeated)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherHarvester: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody("")
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherPlotFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotListFailure)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherPlotRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotListRepeated)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherPlot: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotList)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody("")
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSectionFailure: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotList)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody(resultSectionListFailure)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSectionRepeated: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotList)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody(resultSectionListRepeated)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    private val dispatcherSection: Dispatcher = object : Dispatcher() {
        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/$WEB_SAVE_TOKEN" -> MockResponse().setBody(resultToken)
                "/$WEB_ALL_COLAB" -> MockResponse().setBody(resultColabList)
                "/$WEB_ALL_HARVESTER" -> MockResponse().setBody(resultHarvesterList)
                "/$WEB_ALL_PLOT" -> MockResponse().setBody(resultPlotList)
                "/$WEB_ALL_SECTION" -> MockResponse().setBody(resultSectionList)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_return_data() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_check_msg_field_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA TODOS OS CAMPOS PARA SALVAR AS CONFIGURAÇÕES E ATUALIZAR TODAS AS BASES DE DADOS.")

            composeTestRule.waitUntilTimeout()
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_not_returned() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_token_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherTokenFailure
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE RECUPERACAO DE TOKEN! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.token -> ISendDataConfig -> IConfigRepository.send -> IConfigRetrofitDatasource.recoverToken -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 11 path \$.idServ")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_token_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherToken
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val entity = resultGet.getOrNull()!!
            assertEquals(
                entity,
                ConfigSharedPreferencesModel(
                    number = 16997417840,
                    password = "12345",
                    idServ = 1,
                    version = "1.0",
                    statusSend = StatusSend.STARTED,
                    flagUpdate = FlagUpdate.OUTDATED
                )
            )

            composeTestRule.waitUntilTimeout(3_000)
        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherColabFailure
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 15 path \$[0].regColab")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherColabRepeated
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.addAll -> IColabRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.regColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_colab_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherColab
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val roomModelList = colabDao.all()
            assertEquals(
                roomModelList.count(),
                2
            )
            val roomModel1 = roomModelList[0]
            assertEquals(
                roomModel1.regColab,
                1
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.regColab,
                2
            )

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_harvester_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherHarvesterFailure
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 19 path \$[0].nroHarvester")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_harvester_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherHarvesterRepeated
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.addAll -> IHarvesterRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_harvester.nroHarvester (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_harvester_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherHarvester
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val roomModelList = harvesterDao.all()
            assertEquals(
                roomModelList.count(),
                2
            )
            val roomModel1 = roomModelList[0]
            assertEquals(
                roomModel1.nroHarvester,
                1
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.nroHarvester,
                2
            )

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_plot_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherPlotFailure
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 13 path \$[0].idPlot")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_plot_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherPlotRepeated
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.addAll -> IPlotRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_plot.idPlot (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_plot_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherPlot
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableSection -> ISectionRepository.listAll -> ISectionRetrofitDatasource.listAll -> java.io.EOFException: End of input at line 1 column 1 path \$")

            composeTestRule.waitUntilTimeout(3_000)

            val roomModelList = plotDao.all()
            assertEquals(
                roomModelList.count(),
                2
            )
            val roomModel1 = roomModelList[0]
            assertEquals(
                roomModel1.idPlot,
                1
            )
            assertEquals(
                roomModel1.codPlot,
                1
            )
            assertEquals(
                roomModel1.idSection,
                1
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.idPlot,
                2
            )
            assertEquals(
                roomModel2.codPlot,
                2
            )
            assertEquals(
                roomModel2.idSection,
                1
            )

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_section_incorrect() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherSectionFailure
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableSection -> ISectionRepository.listAll -> ISectionRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 16 path \$[0].idSection")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_section_repeated() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherSectionRepeated
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. ConfigViewModel.updateAllDatabase -> IUpdateTableSection -> ISectionRepository.addAll -> ISectionRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_section.idSection (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout(3_000)

        }

    @Test
    fun check_open_screen_and_msg_if_web_service_return_data_section_correct() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.dispatcher = dispatcherSection
            server.start()
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag(TAG_NUMBER_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("16997417840")
            composeTestRule.onNodeWithTag(TAG_PASSWORD_TEXT_FIELD_CONFIG_SCREEN)
                .performTextInput("12345")
            composeTestRule.onNodeWithText("SALVAR")
                .performClick()

            composeTestRule.waitUntilTimeout(3_000)

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CONFIGURAÇÃO REALIZADA COM SUCESSO!")

            composeTestRule.waitUntilTimeout(3_000)

            val roomModelList = sectionDao.all()
            assertEquals(
                roomModelList.count(),
                2
            )
            val roomModel1 = roomModelList[0]
            assertEquals(
                roomModel1.idSection,
                1
            )
            assertEquals(
                roomModel1.codSection,
                1
            )
            val roomModel2 = roomModelList[1]
            assertEquals(
                roomModel2.idSection,
                2
            )
            assertEquals(
                roomModel2.codSection,
                2
            )
        }

    private fun setContent() {
        composeTestRule.setContent {
            ConfigScreen(
                onNavInitialMenu = {},
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "123456",
                idServ = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        if (level == 1) return

    }
}