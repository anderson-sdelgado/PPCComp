package br.com.usinasantafe.ppc

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.MainActivity
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject
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
                regAuditor1 = 19759,
                regAuditor2 = null,
                regAuditor3 = null,
                date = Date(),
                nroTurn = 1,
                nroOS = 123456,
                codSection = 100,
                nroHarvester = 300,
                nroPlot = 3,
                codFront = 2,
                regOperator = 19035
            )
        )
    }

}