package br.com.usinasantafe.ppc.presenter.view.header.front

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class FrontScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

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
            composeTestRule.onNodeWithTag("text_alert_dialog_simple").assertTextEquals("CAMPO VAZIO! POR FAVOR, PREENCHA O CAMPO \"NRO FRENTE\" PARA DAR CONTINUIDADE AO APONTAMENTO.")

            composeTestRule.waitUntilTimeout()

        }

    @Test
    fun check_data_if_set_execute_successfully() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultGet = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.codFront,
                3
            )

            composeTestRule.waitUntilTimeout()

        }

    private fun setContent(){
        composeTestRule.setContent {
            FrontScreen(
                onNavPlot = {},
                onNavHarvester = {}
            )
        }
    }


}