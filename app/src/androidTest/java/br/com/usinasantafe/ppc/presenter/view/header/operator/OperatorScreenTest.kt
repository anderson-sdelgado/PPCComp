package br.com.usinasantafe.ppc.presenter.view.header.operator

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class OperatorScreenTest {

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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    private val todayLocalDate: LocalDate = LocalDate.now(ZoneId.systemDefault())
    private val localMidnightZonedDateTime: ZonedDateTime = todayLocalDate.atStartOfDay(ZoneId.systemDefault())
    private val midnightDateObject: Date = Date.from(localMidnightZonedDateTime.toInstant())


    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_failure_return_if_field_is_empty() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"MATRICULA OPERADOR\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_not_have_data_in_config_shared_preferences() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_return_failure() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_token_is_invalid() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_have_error_404() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_incorrect() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultColabListFailure)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 15 path \$[0].regColab")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_repeated() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultColabListRepeated)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.addAll -> IColabRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.regColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_success() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultColabList)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("Atualização de dados realizado com sucesso!")

            composeTestRule.waitUntilTimeout()

            val list = colabDao.all()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0].regColab,
                1
            )
            assertEquals(
                list[1].regColab,
                2
            )

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_returned_failure_if_no_data_was_searched() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(1)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"MATRICULA OPERADOR\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_not_have_data_in_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(2)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. OperatorViewModel.setNroOperator -> ISetOperatorHeader -> IAnalysisRepository.setOperatorHeader -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

            val resultGet = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.regOperator,
                19759L
            )

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_date_return_if_all_data_is_correct() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(3)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()


            val resultGet = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.regOperator,
                19759L
            )

            val resultListHeader = headerDao.all()
            assertEquals(
                resultListHeader.size,
                1
            )
            assertEquals(
                resultListHeader[0].regAuditor1,
                12345L
            )
            assertEquals(
                resultListHeader[0].regAuditor2,
                null
            )
            assertEquals(
                resultListHeader[0].regAuditor3,
                null
            )
            assertEquals(
                resultListHeader[0].nroTurn,
                1
            )
            assertEquals(
                resultListHeader[0].date,
                midnightDateObject
            )
            assertEquals(
                resultListHeader[0].codSection,
                325
            )
            assertEquals(
                resultListHeader[0].nroPlot,
                1
            )
            assertEquals(
                resultListHeader[0].nroOS,
                123456
            )
            assertEquals(
                resultListHeader[0].codFront,
                2
            )
            assertEquals(
                resultListHeader[0].nroHarvester,
                100
            )
            assertEquals(
                resultListHeader[0].regOperator,
                19759L
            )
            assertEquals(
                resultListHeader[0].status,
                Status.OPEN
            )


            composeTestRule.waitUntilTimeout()

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

        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    regColab = 19759,
                ),
                ColabRoomModel(
                    regColab = 12345,
                )
            )
        )

        if (level == 2) return

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                regAuditor1 = 12345,
                date = midnightDateObject,
                nroTurn = 1,
                codSection = 325,
                nroPlot = 1,
                nroOS = 123456,
                codFront = 2,
                nroHarvester = 100,
                regOperator = 19759
            )
        )

        if (level == 3) return

    }

    private fun setContent(){
        composeTestRule.setContent {
            OperatorScreen(
                onNavHarvester = {},
                onNavHeaderList = {}
            )
        }
    }


}