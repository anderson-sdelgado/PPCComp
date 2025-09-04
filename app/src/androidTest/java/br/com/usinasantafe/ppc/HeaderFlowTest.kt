package br.com.usinasantafe.ppc

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.MainActivity
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
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

    @Test
    fun header_flow() =
        runTest(
            timeout = 10.minutes
        ) {

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

            Log.d("TestDebug", "Position 22")

            composeTestRule.waitUntilTimeout()


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
                )
            )
        )
    }
}