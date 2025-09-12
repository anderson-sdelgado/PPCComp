package br.com.usinasantafe.ppc.presenter.view.header.harvester

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class HarvesterScreenTest {

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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var harvesterDao: HarvesterDao

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"NRO COLHEDORA\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IGetToken -> java.lang.NullPointerException")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_incorrect() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultHarvesterListFailure)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 19 path \$[0].nroHarvester")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_repeated() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultHarvesterListRepeated)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. HarvesterViewModel.updateAllDatabase -> IUpdateTableHarvester -> IHarvesterRepository.addAll -> IHarvesterRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_harvester.nroHarvester (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_success() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultHarvesterList)
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

            val list = harvesterDao.all()
            assertEquals(
                list.size,
                2
            )
            assertEquals(
                list[0].nroHarvester,
                1
            )
            assertEquals(
                list[1].nroHarvester,
                2
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

    }

    private fun setContent(){
        composeTestRule.setContent {
            HarvesterScreen(
                onNavFront = {},
                onNavOperator = {}
            )
        }
    }

}