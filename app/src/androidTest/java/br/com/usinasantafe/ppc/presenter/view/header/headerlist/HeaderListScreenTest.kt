package br.com.usinasantafe.ppc.presenter.view.header.headerlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class HeaderListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Test
    fun check_open_screen_without_register() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONTÉM CABEÇALHO CADASTRADO.")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_have_only_header_open() =
        runTest {

            hiltRule.inject()

            initialRegister(1)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("NÃO CONTÉM AMOSTRAS")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_open_screen_and_have_header_open_and_sample() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("CONTÉM 2 AMOSTRA(S)")
                .assertIsDisplayed()

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            HeaderListScreen(
                onNavAuditor = {},
                onNavInitialMenu = {},
                onNavSampleList = {}
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
            ),
        )
        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 10,
                regAuditor2 = 20,
                regAuditor3 = 30,
                date = Date(),
                nroTurn = 2,
                codSection = 2,
                nroPlot = 2,
                nroOS = 2,
                codFront = 2,
                nroHarvester = 2,
                regOperator = 2,
                status = Status.CLOSE
            ),
        )

        if (level == 1) return

        sampleDao.insert(
            SampleRoomModel(
                idHeader = 1,
                tare = 1,
                billet = 1,
                wholeCane = 1,
                stump = 1,
                chunk = 1,
                pointer = 1,
                chips = 1,
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true,
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = true,
                mucuna = true,
                silkGrass = true,
            )
        )
        sampleDao.insert(
            SampleRoomModel(
                idHeader = 1,
                tare = 2,
                billet = 2,
                wholeCane = 2,
                stump = 2,
                chunk = 2,
                pointer = 2,
                chips = 2,
                stone = true,
                treeStump = true,
                weed = true,
                anthill = true,
                guineaGrass = false,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = true,
                silkGrass = true,
            )
        )
        sampleDao.insert(
            SampleRoomModel(
                idHeader = 2,
                tare = 3,
                billet = 2,
                wholeCane = 2,
                stump = 2,
                chunk = 3,
                pointer = 2,
                chips = 2,
                stone = true,
                treeStump = false,
                weed = true,
                anthill = true,
                guineaGrass = false,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = true,
                silkGrass = true,
            )
        )

        if (level == 2) return

    }

}