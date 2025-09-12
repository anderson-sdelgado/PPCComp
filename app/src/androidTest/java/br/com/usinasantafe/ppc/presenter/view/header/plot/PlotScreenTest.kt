package br.com.usinasantafe.ppc.presenter.view.header.plot

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
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
class PlotScreenTest {

    private val resultPlotListIncorrect = """
        [
          {"idPlot":1a,"codPlot":1,"idSection":1},
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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var plotDao: PlotDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var sectionDao: SectionDao

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"NRO TALHÃO\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IGetToken -> java.lang.NullPointerException")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$")

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_incorrect() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultPlotListIncorrect)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.listAll -> IPlotRetrofitDatasource.listAll -> com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 2 column 13 path \$[0].idPlot")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_repeated() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultPlotListRepeated)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("ATUALIZAR DADOS").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA DE ATUALIZAÇÃO DE DADOS! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.updateAllDatabase -> IUpdateTablePlot -> IPlotRepository.addAll -> IPlotRoomDatasource.addAll -> android.database.sqlite.SQLiteConstraintException: UNIQUE constraint failed: tb_plot.idPlot (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY[1555])")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_update_failure_return_if_web_service_returns_data_success() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultPlotList)
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

            val list = plotDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idPlot,
                1
            )
            assertEquals(
                model1.nroPlot,
                1
            )
            assertEquals(
                model1.idSection,
                1
            )
            val model2 = list[1]
            assertEquals(
                model2.idPlot,
                2
            )
            assertEquals(
                model2.nroPlot,
                2
            )
            assertEquals(
                model2.idSection,
                1
            )

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_not_have_data_in_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(1)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. PlotViewModel.setNroPlot -> ICheckPlot -> IAnalysisRepository.getSectionHeader -> IHeaderSharedPreferencesDatasource.getSection -> java.lang.NullPointerException")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_not_have_data_in_section_room() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(2)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"NRO TALHÃO\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_not_have_data_in_plot_room() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(3)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"NRO TALHÃO\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_nroPlot_is_incorrect() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(4)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"NRO TALHÃO\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_failure_return_if_idSection_is_incorrect() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(4, true)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("DADO INVÁLIDO! POR FAVOR, VERIFIQUE SE O CAMPO \"NRO TALHÃO\" FOI DIGITADO CORRETAMENTE OU ATUALIZE OS DADOS PARA VERIFICAR SE OS MESMOS NÃO ESTÃO DESATUALIZADOS.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_date_return_if_all_data_is_correct() =
        runTest {

            hiltRule.inject()

            setContent()

            initialRegister(4)

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultGet = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.codSection,
                1
            )
            assertEquals(
                model.nroPlot,
                10
            )

            composeTestRule.waitUntilTimeout()

        }

    private suspend fun initialRegister(level: Int, failure: Boolean = false) {

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

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                codSection = 1,
            )
        )

        if (level == 2) return

        sectionDao.insertAll(
            listOf(
                SectionRoomModel(
                    idSection = if(failure) 2 else 1,
                    codSection = 1
                )
            )
        )

        if (level == 3) return

        plotDao.insertAll(
            listOf(
                PlotRoomModel(
                    idPlot = 1,
                    nroPlot = 1,
                    idSection = 1
                ),
                PlotRoomModel(
                    idPlot = 2,
                    nroPlot = 10,
                    idSection = 1
                )
            )
        )

        if (level == 4) return

    }

    private fun setContent() {
        composeTestRule.setContent {
            PlotScreen(
                onNavFront = {},
                onNavSection = {}
            )
        }
    }

}