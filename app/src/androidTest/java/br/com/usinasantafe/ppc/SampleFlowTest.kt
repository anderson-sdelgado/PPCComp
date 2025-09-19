package br.com.usinasantafe.ppc

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.MainActivity
import br.com.usinasantafe.ppc.presenter.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.Status
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
                3.0
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
                4.0
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

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 1,
                tare = 2.0,
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
                tare = 3.0,
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
                tare = 4.0,
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