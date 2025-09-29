package br.com.usinasantafe.ppc

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.MainActivity
import br.com.usinasantafe.ppc.presenter.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class SampleFlowTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Test
    fun sample_flow() =
        runTest(
            timeout = 10.minutes
        ) {

            hiltRule.inject()

            initialRegister()

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

            composeTestRule.onNodeWithTag("item_list_1")
                .performClick()

            Log.d("TestDebug", "Position 4")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("EXCLUIR")
                .performClick()

            Log.d("TestDebug", "Position 5")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 6")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("item_list_2")
                .performClick()

            Log.d("TestDebug", "Position 7")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("FINALIZAR")
                .performClick()

            Log.d("TestDebug", "Position 8")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 9")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("item_list_3")
                .performClick()

            Log.d("TestDebug", "Position 10")

            composeTestRule.waitUntilTimeout()

            val listHeaderInitial = headerDao.all()
            assertEquals(
                listHeaderInitial.size,
                2
            )
            val model1Initial = listHeaderInitial[0]
            assertEquals(
                model1Initial.regAuditor1,
                18017
            )
            assertEquals(
                model1Initial.regAuditor2,
                null
            )
            assertEquals(
                model1Initial.regAuditor3,
                null
            )
            assertEquals(
                model1Initial.nroTurn,
                3
            )
            assertEquals(
                model1Initial.nroOS,
                123456
            )
            assertEquals(
                model1Initial.codSection,
                100
            )
            assertEquals(
                model1Initial.nroHarvester,
                300
            )
            assertEquals(
                model1Initial.nroPlot,
                3
            )
            assertEquals(
                model1Initial.codFront,
                2
            )
            assertEquals(
                model1Initial.regOperator,
                19035
            )
            assertEquals(
                model1Initial.status,
                Status.FINISH
            )
            assertEquals(
                model1Initial.statusSend,
                StatusSend.SEND
            )
            val model2Initial = listHeaderInitial[1]
            assertEquals(
                model2Initial.regAuditor1,
                19035
            )
            assertEquals(
                model2Initial.regAuditor2,
                null
            )
            assertEquals(
                model2Initial.regAuditor3,
                null
            )
            assertEquals(
                model2Initial.nroTurn,
                2
            )
            assertEquals(
                model2Initial.nroOS,
                123456
            )
            assertEquals(
                model2Initial.codSection,
                100
            )
            assertEquals(
                model2Initial.nroHarvester,
                500
            )
            assertEquals(
                model2Initial.nroPlot,
                3
            )
            assertEquals(
                model2Initial.codFront,
                2
            )
            assertEquals(
                model2Initial.regOperator,
                19035
            )
            assertEquals(
                model2Initial.status,
                Status.OPEN
            )
            assertEquals(
                model2Initial.statusSend,
                StatusSend.STARTED
            )

            val listSampleInitial = sampleDao.all()
            assertEquals(
                listSampleInitial.size,
                2
            )
            val model1SampleInitial = listSampleInitial[0]
            assertEquals(
                model1SampleInitial.idHeader,
                2
            )
            assertEquals(
                model1SampleInitial.tare,
                1.0
            )
            assertEquals(
                model1SampleInitial.stalk,
                3.0
            )
            assertEquals(
                model1SampleInitial.wholeCane,
                3.0
            )
            assertEquals(
                model1SampleInitial.stump,
                3.0
            )
            assertEquals(
                model1SampleInitial.piece,
                3.0
            )
            assertEquals(
                model1SampleInitial.tip,
                3.0
            )
            assertEquals(
                model1SampleInitial.slivers,
                3.0
            )
            assertEquals(
                model1SampleInitial.stone,
                false
            )
            assertEquals(
                model1SampleInitial.treeStump,
                false
            )
            assertEquals(
                model1SampleInitial.weed,
                true
            )
            assertEquals(
                model1SampleInitial.anthill,
                false
            )
            assertEquals(
                model1SampleInitial.guineaGrass,
                true
            )
            assertEquals(
                model1SampleInitial.castorOilPlant,
                true
            )
            assertEquals(
                model1SampleInitial.signalGrass,
                false
            )
            assertEquals(
                model1SampleInitial.mucuna,
                false
            )
            assertEquals(
                model1SampleInitial.silkGrass,
                false
            )
            val model2SampleInitial = listSampleInitial[1]
            assertEquals(
                model2SampleInitial.idHeader,
                3
            )
            assertEquals(
                model2SampleInitial.tare,
                1.0
            )
            assertEquals(
                model2SampleInitial.stalk,
                4.0
            )
            assertEquals(
                model2SampleInitial.wholeCane,
                4.0
            )
            assertEquals(
                model2SampleInitial.stump,
                4.0
            )
            assertEquals(
                model2SampleInitial.piece,
                4.0
            )
            assertEquals(
                model2SampleInitial.tip,
                4.0
            )
            assertEquals(
                model2SampleInitial.slivers,
                4.0
            )
            assertEquals(
                model2SampleInitial.stone,
                false
            )
            assertEquals(
                model2SampleInitial.treeStump,
                false
            )
            assertEquals(
                model2SampleInitial.weed,
                true
            )
            assertEquals(
                model2SampleInitial.anthill,
                false
            )
            assertEquals(
                model2SampleInitial.guineaGrass,
                true
            )
            assertEquals(
                model2SampleInitial.castorOilPlant,
                true
            )
            assertEquals(
                model2SampleInitial.signalGrass,
                false
            )
            assertEquals(
                model2SampleInitial.mucuna,
                false
            )
            assertEquals(
                model2SampleInitial.silkGrass,
                false
            )

            Log.d("TestDebug", "Position 11")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("item_list_4")
                .performClick()

            Log.d("TestDebug", "Position 12")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 13")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR")
                .performClick()

            Log.d("TestDebug", "Position 14")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("item_list_3")
                .performClick()

            Log.d("TestDebug", "Position 15")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("INSERIR")
                .performClick()

            Log.d("TestDebug", "Position 16")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 17")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("INSERIR")
                .performClick()

            Log.d("TestDebug", "Position 18")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 19")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 20")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 21")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 22")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 23")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 24")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 25")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 26")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 27")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 28")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 29")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 30")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 31")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 32")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 33")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 34")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 35")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 36")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 37")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 38")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("9").performClick()

            Log.d("TestDebug", "Position 39")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 40")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 41")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("9").performClick()

            Log.d("TestDebug", "Position 42")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 43")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 44")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 45")

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            Log.d("TestDebug", "Position 46")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 47")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 48")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("4").performClick()

            Log.d("TestDebug", "Position 49")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 50")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR").performClick()

            Log.d("TestDebug", "Position 51")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("9").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("4").performClick()

            Log.d("TestDebug", "Position 52")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 53")

            composeTestRule.waitUntilTimeout()

            val resulFieldSampleModel = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resulFieldSampleModel.isSuccess,
                true
            )
            val fieldSampleModel = resulFieldSampleModel.getOrNull()!!
            assertEquals(
                fieldSampleModel.tare,
                1.01
            )
            assertEquals(
                fieldSampleModel.stalk,
                2.01
            )
            assertEquals(
                fieldSampleModel.wholeCane,
                null
            )
            assertEquals(
                fieldSampleModel.stump,
                2.11
            )
            assertEquals(
                fieldSampleModel.piece,
                3.129
            )
            assertEquals(
                fieldSampleModel.tip,
                null
            )
            assertEquals(
                fieldSampleModel.slivers,
                2.904
            )
            assertEquals(
                fieldSampleModel.stone,
                false
            )
            assertEquals(
                fieldSampleModel.treeStump,
                false
            )
            assertEquals(
                fieldSampleModel.weed,
                false
            )
            assertEquals(
                fieldSampleModel.anthill,
                false
            )
            assertEquals(
                fieldSampleModel.guineaGrass,
                false
            )
            assertEquals(
                fieldSampleModel.castorOilPlant,
                false
            )
            assertEquals(
                fieldSampleModel.signalGrass,
                false
            )
            assertEquals(
                fieldSampleModel.mucuna,
                false
            )
            assertEquals(
                fieldSampleModel.silkGrass,
                false
            )

            Log.d("TestDebug", "Position 53")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("PEDRA").performClick()
            composeTestRule.onNodeWithText("FORMIGUEIROS").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 54")

            composeTestRule.waitUntilTimeout()

            val resulObsSampleModel = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resulObsSampleModel.isSuccess,
                true
            )
            val fieldObsModel = resulObsSampleModel.getOrNull()!!
            assertEquals(
                fieldObsModel.tare,
                1.01
            )
            assertEquals(
                fieldObsModel.stalk,
                2.01
            )
            assertEquals(
                fieldObsModel.wholeCane,
                null
            )
            assertEquals(
                fieldObsModel.stump,
                2.11
            )
            assertEquals(
                fieldObsModel.piece,
                3.129
            )
            assertEquals(
                fieldObsModel.tip,
                null
            )
            assertEquals(
                fieldObsModel.slivers,
                2.904
            )
            assertEquals(
                fieldObsModel.stone,
                true
            )
            assertEquals(
                fieldObsModel.treeStump,
                false
            )
            assertEquals(
                fieldObsModel.weed,
                false
            )
            assertEquals(
                fieldObsModel.anthill,
                true
            )
            assertEquals(
                fieldObsModel.guineaGrass,
                false
            )
            assertEquals(
                fieldObsModel.castorOilPlant,
                false
            )
            assertEquals(
                fieldObsModel.signalGrass,
                false
            )
            assertEquals(
                fieldObsModel.mucuna,
                false
            )
            assertEquals(
                fieldObsModel.silkGrass,
                false
            )

            Log.d("TestDebug", "Position 53")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("INSERIR")
                .performClick()

            Log.d("TestDebug", "Position 54")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("9").performClick()

            Log.d("TestDebug", "Position 55")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 56")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("9").performClick()

            Log.d("TestDebug", "Position 57")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 58")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("8").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()

            Log.d("TestDebug", "Position 59")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 60")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 61")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 66")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 67")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 68")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("8").performClick()
            composeTestRule.onNodeWithText("8").performClick()
            composeTestRule.onNodeWithText("9").performClick()

            Log.d("TestDebug", "Position 69")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 70")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 71")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            Log.d("TestDebug", "Position 72")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("PEDRA").performClick()
            composeTestRule.onNodeWithText("PLANTAS DANINHAS").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 73")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("RETORNAR").performClick()

            Log.d("TestDebug", "Position 74")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("PEDRA").performClick()
            composeTestRule.onNodeWithText("PLANTAS DANINHAS").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 75")

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CAPIM-COLONIÃO").performClick()
            composeTestRule.onNodeWithText("MUCUNA").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            Log.d("TestDebug", "Position 76")

            composeTestRule.waitUntilTimeout()

            val listAfter = sampleDao.all()
            assertEquals(
                listAfter.size,
                3
            )
            val model1After = listAfter[0]
            assertEquals(
                model1After,
                SampleRoomModel(
                    idHeader = 2,
                    pos = 1,
                    tare = 1.0,
                    stalk = 3.0,
                    wholeCane = 3.0,
                    stump = 3.0,
                    piece = 3.0,
                    tip = 3.0,
                    slivers = 3.0,
                    stone = false,
                    treeStump = false,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = false,
                    mucuna = false,
                    silkGrass = false,
                    id = 3
                )
            )
            val model2After = listAfter[1]
            assertEquals(
                model2After,
                SampleRoomModel(
                    idHeader = 3,
                    pos = 2,
                    tare = 1.01,
                    stalk = 2.01,
                    wholeCane = null,
                    stump = 2.11,
                    piece = 3.129,
                    tip = null,
                    slivers = 2.904,
                    stone = true,
                    treeStump = false,
                    weed = false,
                    anthill = true,
                    guineaGrass = false,
                    castorOilPlant = false,
                    signalGrass = false,
                    mucuna = false,
                    silkGrass = false,
                    id = 5
                )
            )
            val model3After = listAfter[2]
            assertEquals(
                model3After,
                SampleRoomModel(
                    idHeader = 3,
                    pos = 3,
                    tare = 0.509,
                    stalk = 1.249,
                    wholeCane = 2.8,
                    stump = null,
                    piece = null,
                    tip = 3.889,
                    slivers = null,
                    stone = true,
                    treeStump = false,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = false,
                    signalGrass = false,
                    mucuna = true,
                    silkGrass = false,
                    id = 6
                )
            )

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

        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 19759,
                regAuditor2 = null,
                regAuditor3 = null,
                date = Date(),
                nroTurn = 1,
                nroOS = 123456,
                codSection = 100,
                nroHarvester = 200,
                nroPlot = 3,
                codFront = 2,
                regOperator = 19035
            )
        )

        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 18017,
                regAuditor2 = null,
                regAuditor3 = null,
                date = Date(),
                nroTurn = 3,
                nroOS = 123456,
                codSection = 100,
                nroHarvester = 300,
                nroPlot = 3,
                codFront = 2,
                regOperator = 19035
            )
        )


        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 19035,
                regAuditor2 = null,
                regAuditor3 = null,
                date = Date(),
                nroTurn = 2,
                nroOS = 123456,
                codSection = 100,
                nroHarvester = 500,
                nroPlot = 3,
                codFront = 2,
                regOperator = 19035
            )
        )

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 1,
                pos = 1,
                tare = 1.02,
                stalk = 2.0,
                wholeCane = 1.50,
                stump = 3.0,
                piece = 1.560,
                tip = 1.054,
                slivers = 1.024,
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

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 1,
                pos = 2,
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 2.0,
                stump = 2.0,
                piece = 2.0,
                tip = 2.0,
                slivers = 2.0,
                stone = false,
                treeStump = false,
                weed = false,
                anthill = false,
                guineaGrass = false,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = false,
                silkGrass = false
            )
        )

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 2,
                pos = 1,
                tare = 1.0,
                stalk = 3.0,
                wholeCane = 3.0,
                stump = 3.0,
                piece = 3.0,
                tip = 3.0,
                slivers = 3.0,
                stone = false,
                treeStump = false,
                weed = true,
                anthill = false,
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = false,
                silkGrass = false
            )
        )

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 3,
                pos = 1,
                tare = 1.0,
                stalk = 4.0,
                wholeCane = 4.0,
                stump = 4.0,
                piece = 4.0,
                tip = 4.0,
                slivers = 4.0,
                stone = false,
                treeStump = false,
                weed = true,
                anthill = false,
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = false,
                silkGrass = false
            )
        )

    }

}