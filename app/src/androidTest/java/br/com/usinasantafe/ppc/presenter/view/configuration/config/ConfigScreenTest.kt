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
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
            server.start()
            server.enqueue(
                MockResponse().setBody("""{"idServ":1a}""")
            )
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
            server.start()
            server.enqueue(
                MockResponse().setBody("""{"idServ":1}""")
            )
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

            composeTestRule.waitUntilTimeout(10_000)

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