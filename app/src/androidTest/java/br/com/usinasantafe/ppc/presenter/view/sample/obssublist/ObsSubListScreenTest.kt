package br.com.usinasantafe.ppc.presenter.view.sample.obssublist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ObsSubListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Inject
    lateinit var sampleDao: SampleDao

    @Inject
    lateinit var headerDao: HeaderDao

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CAPIM-COLONIÃO").performClick()
            composeTestRule.onNodeWithText("MUCUNA").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ObsSubListViewModel.setSubObs -> ISetSubObsSample -> IAnalysisRepository.saveSample -> java.lang.IllegalArgumentException: Field 'tare' cannot be null.")

            composeTestRule.waitUntilTimeout()

            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                null
            )
            assertEquals(
                modelAfter.stone,
                false
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                false
            )
            assertEquals(
                modelAfter.anthill,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                0
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_failure_if_not_have_header_open() =
        runTest {

            hiltRule.inject()

            initialRegister()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CAPIM-COLONIÃO").performClick()
            composeTestRule.onNodeWithText("MUCUNA").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertIsDisplayed()
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("FALHA INESPERADA NO APLICATIVO! POR FAVOR ENTRE EM CONTATO COM TI. ObsSubListViewModel.setSubObs -> ISetSubObsSample -> IAnalysisRepository.saveSample -> java.lang.IllegalArgumentException: The field 'idHeader' cannot is null.")

            composeTestRule.waitUntilTimeout()

            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                1.0
            )
            assertEquals(
                modelAfter.stalk,
                2.0
            )
            assertEquals(
                modelAfter.wholeCane,
                3.0
            )
            assertEquals(
                modelAfter.stump,
                4.0
            )
            assertEquals(
                modelAfter.piece,
                5.0
            )
            assertEquals(
                modelAfter.tip,
                6.0
            )
            assertEquals(
                modelAfter.slivers,
                7.0
            )
            assertEquals(
                modelAfter.stone,
                true
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                true
            )
            assertEquals(
                modelAfter.anthill,
                false
            )
            assertEquals(
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                false
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                true
            )
            assertEquals(
                modelAfter.silkGrass,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                0
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_success_if_have_header_open() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CAPIM-COLONIÃO").performClick()
            composeTestRule.onNodeWithText("MUCUNA").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                1.0
            )
            assertEquals(
                modelAfter.stalk,
                2.0
            )
            assertEquals(
                modelAfter.wholeCane,
                3.0
            )
            assertEquals(
                modelAfter.stump,
                4.0
            )
            assertEquals(
                modelAfter.piece,
                5.0
            )
            assertEquals(
                modelAfter.tip,
                6.0
            )
            assertEquals(
                modelAfter.slivers,
                7.0
            )
            assertEquals(
                modelAfter.stone,
                true
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                true
            )
            assertEquals(
                modelAfter.anthill,
                false
            )
            assertEquals(
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                false
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                true
            )
            assertEquals(
                modelAfter.silkGrass,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.tare,
                1.0
            )
            assertEquals(
                model.stalk,
                2.0
            )
            assertEquals(
                model.wholeCane,
                3.0
            )
            assertEquals(
                model.stump,
                4.0
            )
            assertEquals(
                model.piece,
                5.0
            )
            assertEquals(
                model.tip,
                6.0
            )
            assertEquals(
                model.slivers,
                7.0
            )
            assertEquals(
                model.stone,
                true
            )
            assertEquals(
                model.treeStump,
                false
            )
            assertEquals(
                model.weed,
                true
            )
            assertEquals(
                model.anthill,
                false
            )
            assertEquals(
                model.guineaGrass,
                true
            )
            assertEquals(
                model.castorOilPlant,
                false
            )
            assertEquals(
                model.signalGrass,
                false
            )
            assertEquals(
                model.mucuna,
                true
            )
            assertEquals(
                model.silkGrass,
                false
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            ObsSubListScreen(
                onObsList = {},
                onNavSampleList = {}
            )
        }
    }


    private suspend fun initialRegister(level: Int = 1) {

        sampleSharedPreferencesDatasource.save(
            SampleSharedPreferencesModel(
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 3.0,
                stump = 4.0,
                piece = 5.0,
                tip = 6.0,
                slivers = 7.0,
                stone = true,
                treeStump = false,
                weed = true,
                anthill = false,
                guineaGrass = false,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = false,
                silkGrass = false
            )
        )

        if (level == 1) return

        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 1,
                regAuditor2 = 2,
                regAuditor3 = 3,
                date = Date(),
                nroTurn = 4,
                codSection = 5,
                nroPlot = 6,
                nroOS = 7,
                codFront = 8,
                nroHarvester = 9,
                regOperator = 10,
                status = Status.OPEN
            )
        )

        if (level == 2) return

    }

}