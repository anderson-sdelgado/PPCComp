package br.com.usinasantafe.ppc.presenter.view.header.auditor

import android.annotation.SuppressLint
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.domain.usecases.header.CheckColab
import br.com.usinasantafe.ppc.domain.usecases.header.SetAuditorHeader
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.Args.POS_AUDITOR_ARGS
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AuditorScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var setAuditorHeader: SetAuditorHeader

    @Inject
    lateinit var updateTableColab: UpdateTableColab

    @Inject
    lateinit var checkColab: CheckColab

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

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

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_failure_in_update_if_not_have_data_config() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. AuditorViewModel.updateAllDatabase -> IUpdateTableColab -> IGetToken -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_failure_in_update_if_web_service_have_failure() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. AuditorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_failure_in_update_if_web_service_return_incorrect() =
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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. AuditorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_failure_in_update_if_web_service_return_json_incorrect() =
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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. AuditorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.listAll -> IColabRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 15 path \$[0].regColab")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_failure_in_update_if_web_service_return_data_repeated() =
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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. AuditorViewModel.updateAllDatabase -> IUpdateTableColab -> IColabRepository.addAll -> IColabRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_colab.regColab (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_update_execute_successfully() =
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

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_msg_if_field_empty() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"MATRICULA AUDITOR 1\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_msg_if_reg_incorrect() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"MATRICULA AUDITOR 1\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_set_auditor_1() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

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
                model.regAuditor1,
                19759L
            )

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_open_screen_and_set_auditor_1_and_2() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultGet1 = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet1.isSuccess,
                true
            )
            val model1 = resultGet1.getOrNull()!!
            assertEquals(
                model1.regAuditor1,
                19759L
            )
            assertEquals(
                model1.regAuditor2,
                null
            )

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("8").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultGet2 = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet2.isSuccess,
                true
            )
            val model2 = resultGet2.getOrNull()!!
            assertEquals(
                model2.regAuditor1,
                19759L
            )
            assertEquals(
                model2.regAuditor2,
                18017L
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
                    regColab = 19759
                )
            )
        )

        if (level == 2) return

        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    regColab = 18017
                )
            )
        )

        if (level == 3) return
    }

    @SuppressLint("ViewModelConstructorInComposable")
    private fun setContent(){
        composeTestRule.setContent {
            AuditorScreen(
                viewModel = AuditorViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            POS_AUDITOR_ARGS to 1
                        )
                    ),
                    updateTableColab = updateTableColab,
                    setAuditorHeader = setAuditorHeader,
                    checkColab = checkColab
                ),
                onNavHeaderList = {},
                onNavDate = {}
            )
        }
    }

}