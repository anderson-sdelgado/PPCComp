package br.com.usinasantafe.ppc

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.MainActivity
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class HeaderFlowTest {

    private val resultOS = """
          {"nroOS":123456,"idSection":2}
    """.trimIndent()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var colabDao: ColabDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var sectionDao: SectionDao

    @Inject
    lateinit var plotDao: PlotDao

    @Inject
    lateinit var harvesterDao: HarvesterDao

    @Inject
    lateinit var headerDao: HeaderDao

    @Test
    fun header_flow() =
        runTest(
            timeout = 10.minutes
        ) {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOS)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister()

            val todayLocalDate: LocalDate = LocalDate.now(ZoneId.systemDefault())
            val localMidnightZonedDateTime: ZonedDateTime = todayLocalDate.atStartOfDay(ZoneId.systemDefault())
            val midnightDateObject: Date = Date.from(localMidnightZonedDateTime.toInstant())

            Log.d("TestDebug", "Position 1")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("CONFIGURAÇÃO")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("SAIR")
                .assertIsDisplayed()

            Log.d("TestDebug", "Position 2")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 3")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR")
                .performClick()

            Log.d("TestDebug", "Position 4")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            Log.d("TestDebug", "Position 5")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("INSERIR")
                .performClick()

            Log.d("TestDebug", "Position 6")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 7")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("INSERIR")
                .performClick()

            Log.d("TestDebug", "Position 8")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 9")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 10")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 11")

            composeTestRule.waitUntilTimeout()

            val resultGetAuditor1 = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAuditor1.isSuccess,
                true
            )
            val modelAuditor1 = resultGetAuditor1.getOrNull()!!
            assertEquals(
                modelAuditor1.regAuditor1,
                19759L
            )

            Log.d("TestDebug", "Position 12")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 13")

            composeTestRule.waitUntilTimeout()

            val resultGetAuditor2 = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAuditor2.isSuccess,
                true
            )
            val modelAuditor2 = resultGetAuditor2.getOrNull()!!
            assertEquals(
                modelAuditor2.regAuditor1,
                19759L
            )
            assertEquals(
                modelAuditor2.regAuditor2,
                null
            )
            assertEquals(
                modelAuditor2.regAuditor3,
                null
            )

            Log.d("TestDebug", "Position 14")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CANCELAR").performClick()

            Log.d("TestDebug", "Position 15")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("7").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 16")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 17")

            composeTestRule.waitUntilTimeout()

            val resultGetAuditor2Ret = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAuditor2Ret.isSuccess,
                true
            )
            val modelAuditor2Ret = resultGetAuditor2Ret.getOrNull()!!
            assertEquals(
                modelAuditor2Ret.regAuditor1,
                19759L
            )
            assertEquals(
                modelAuditor2Ret.regAuditor2,
                null
            )
            assertEquals(
                modelAuditor2Ret.regAuditor3,
                null
            )

            Log.d("TestDebug", "Position 18")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 19")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR").performClick()

            Log.d("TestDebug", "Position 20")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 21")

            composeTestRule.waitUntilTimeout()

            val resultGetDate = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetDate.isSuccess,
                true
            )
            val modelDate = resultGetDate.getOrNull()!!
            assertEquals(
                modelDate.regAuditor1,
                19759L
            )
            assertEquals(
                modelDate.regAuditor2,
                null
            )
            assertEquals(
                modelDate.regAuditor3,
                null
            )
            assertEquals(
                modelDate.date,
                midnightDateObject
            )

            Log.d("TestDebug", "Position 22")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("TURNO 1").performClick()

            Log.d("TestDebug", "Position 23")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 24")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("TURNO 1").performClick()

            Log.d("TestDebug", "Position 25")

            composeTestRule.waitUntilTimeout()

            val resultGetTurn = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetTurn.isSuccess,
                true
            )
            val modelTurn = resultGetTurn.getOrNull()!!
            assertEquals(
                modelTurn.regAuditor1,
                19759L
            )
            assertEquals(
                modelTurn.regAuditor2,
                null
            )
            assertEquals(
                modelTurn.regAuditor3,
                null
            )
            assertEquals(
                modelTurn.date,
                midnightDateObject
            )
            assertEquals(
                modelTurn.nroTurn,
                1
            )

            Log.d("TestDebug", "Position 26")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("6").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 27")

            composeTestRule.waitUntilTimeout()

            val resultGetOS = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetOS.isSuccess,
                true
            )
            val modelOS = resultGetOS.getOrNull()!!
            assertEquals(
                modelOS.regAuditor1,
                19759L
            )
            assertEquals(
                modelOS.regAuditor2,
                null
            )
            assertEquals(
                modelOS.regAuditor3,
                null
            )
            assertEquals(
                modelOS.date,
                midnightDateObject
            )
            assertEquals(
                modelOS.nroTurn,
                1
            )
            assertEquals(
                modelOS.nroOS,
                123456
            )

            Log.d("TestDebug", "Position 28")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            Log.d("TestDebug", "Position 29")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 30")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 31")

            composeTestRule.waitUntilTimeout()

            val resultGetSection = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetSection.isSuccess,
                true
            )
            val modelSection = resultGetSection.getOrNull()!!
            assertEquals(
                modelSection.regAuditor1,
                19759L
            )
            assertEquals(
                modelSection.regAuditor2,
                null
            )
            assertEquals(
                modelSection.regAuditor3,
                null
            )
            assertEquals(
                modelSection.date,
                midnightDateObject
            )
            assertEquals(
                modelSection.nroTurn,
                1
            )
            assertEquals(
                modelSection.nroOS,
                123456
            )
            assertEquals(
                modelSection.codSection,
                300
            )

            Log.d("TestDebug", "Position 32")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 33")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 34")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 35")

            composeTestRule.waitUntilTimeout()

            val resultGetPlot = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetPlot.isSuccess,
                true
            )
            val modelPlot = resultGetPlot.getOrNull()!!
            assertEquals(
                modelPlot.regAuditor1,
                19759L
            )
            assertEquals(
                modelPlot.regAuditor2,
                null
            )
            assertEquals(
                modelPlot.regAuditor3,
                null
            )
            assertEquals(
                modelPlot.date,
                midnightDateObject
            )
            assertEquals(
                modelPlot.nroTurn,
                1
            )
            assertEquals(
                modelPlot.nroOS,
                123456
            )
            assertEquals(
                modelPlot.codSection,
                300
            )
            assertEquals(
                modelPlot.nroPlot,
                10
            )

            Log.d("TestDebug", "Position 36")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 37")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 38")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 39")

            composeTestRule.waitUntilTimeout()

            val resultGetFront = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetFront.isSuccess,
                true
            )
            val modelFront = resultGetFront.getOrNull()!!
            assertEquals(
                modelFront.regAuditor1,
                19759L
            )
            assertEquals(
                modelFront.regAuditor2,
                null
            )
            assertEquals(
                modelFront.regAuditor3,
                null
            )
            assertEquals(
                modelFront.date,
                midnightDateObject
            )
            assertEquals(
                modelFront.nroTurn,
                1
            )
            assertEquals(
                modelFront.nroOS,
                123456
            )
            assertEquals(
                modelFront.codSection,
                300
            )
            assertEquals(
                modelFront.nroPlot,
                10
            )
            assertEquals(
                modelFront.codFront,
                2
            )

            Log.d("TestDebug", "Position 40")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 41")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 42")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 43")

            composeTestRule.waitUntilTimeout()

            val resultGetHarvester = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetHarvester.isSuccess,
                true
            )
            val modelHarvester = resultGetHarvester.getOrNull()!!
            assertEquals(
                modelHarvester.regAuditor1,
                19759L
            )
            assertEquals(
                modelHarvester.regAuditor2,
                null
            )
            assertEquals(
                modelHarvester.regAuditor3,
                null
            )
            assertEquals(
                modelHarvester.date,
                midnightDateObject
            )
            assertEquals(
                modelHarvester.nroTurn,
                1
            )
            assertEquals(
                modelHarvester.nroOS,
                123456
            )
            assertEquals(
                modelHarvester.codSection,
                300
            )
            assertEquals(
                modelHarvester.nroPlot,
                10
            )
            assertEquals(
                modelHarvester.codFront,
                2
            )
            assertEquals(
                modelHarvester.nroHarvester,
                200
            )

            Log.d("TestDebug", "Position 44")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("8").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 45")

            composeTestRule.waitUntilTimeout()

            val resultGetOperator = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGetHarvester.isSuccess,
                true
            )
            val modelOperator = resultGetOperator.getOrNull()!!
            assertEquals(
                modelOperator.regAuditor1,
                19759L
            )
            assertEquals(
                modelOperator.regAuditor2,
                null
            )
            assertEquals(
                modelOperator.regAuditor3,
                null
            )
            assertEquals(
                modelOperator.date,
                midnightDateObject
            )
            assertEquals(
                modelOperator.nroTurn,
                1
            )
            assertEquals(
                modelOperator.nroOS,
                123456
            )
            assertEquals(
                modelOperator.codSection,
                300
            )
            assertEquals(
                modelOperator.nroPlot,
                10
            )
            assertEquals(
                modelOperator.codFront,
                2
            )
            assertEquals(
                modelOperator.nroHarvester,
                200
            )
            assertEquals(
                modelOperator.regOperator,
                18035L
            )

            val list = headerDao.all()
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
                model.regAuditor1,
                19759L
            )
            assertEquals(
                model.regAuditor2,
                null
            )
            assertEquals(
                model.regAuditor3,
                null
            )
            assertEquals(
                model.date,
                midnightDateObject
            )
            assertEquals(
                model.nroTurn,
                1
            )
            assertEquals(
                model.nroOS,
                123456
            )
            assertEquals(
                model.codSection,
                300
            )
            assertEquals(
                model.nroPlot,
                10
            )
            assertEquals(
                model.codFront,
                2
            )
            assertEquals(
                model.nroHarvester,
                200
            )
            assertEquals(
                model.regOperator,
                18035L
            )
            assertEquals(
                model.status,
                Status.CLOSE
            )

            Log.d("TestDebug", "Position 46")

            composeTestRule.waitUntilTimeout(10_000)

        }

    private suspend fun initialRegister() {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                idServ = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        colabDao.insertAll(
            listOf(
                ColabRoomModel(
                    regColab = 19759
                ),
                ColabRoomModel(
                    regColab = 18035
                )
            )
        )

        sectionDao.insertAll(
            listOf(
                SectionRoomModel(
                    idSection = 1,
                    codSection = 100,
                ),
                SectionRoomModel(
                    idSection = 2,
                    codSection = 300,
                )
            )
        )

        plotDao.insertAll(
            listOf(
                PlotRoomModel(
                    idPlot = 100,
                    nroPlot = 1,
                    idSection = 1
                ),
                PlotRoomModel(
                    idPlot = 102,
                    nroPlot = 10,
                    idSection = 2
                )
            )
        )

        harvesterDao.insertAll(
            listOf(
                HarvesterRoomModel(
                    nroHarvester = 200
                )
            )
        )

    }
}