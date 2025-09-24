package br.com.usinasantafe.ppc.presenter.view.sample.samplelist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.presenter.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class SampleListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(20_000)

        }

    @Test
    fun check_recover_empty_list() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout(20_000)

        }

    @Test
    fun check_recover_list_with_data() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout(20_000)

        }

    @Test
    fun check_delete_item() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag("item_list_1").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            composeTestRule.waitUntilTimeout(20_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            SampleListScreen(
                onNavHeaderList = {},
                onNavFieldSample = {}
            )
        }
    }

    private suspend fun initialRegister(level: Int) {

        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 1,
                regAuditor2 = 2,
                regAuditor3 = 3,
                date = Date(),
                nroTurn = 1,
                codSection = 1,
                nroPlot = 1,
                nroOS = 1,
                codFront = 1,
                nroHarvester = 1,
                regOperator = 1,
                status = Status.OPEN
            )
        )

        if(level == 1) return

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
                idHeader = 1,
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

        if(level == 2) return

    }

}