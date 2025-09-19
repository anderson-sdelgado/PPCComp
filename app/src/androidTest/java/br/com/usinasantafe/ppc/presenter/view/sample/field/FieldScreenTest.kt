package br.com.usinasantafe.ppc.presenter.view.sample.field

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import kotlin.test.Test

@HiltAndroidTest
class FieldScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_digit() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("4").performClick()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_clear() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("5").performClick()

            composeTestRule.waitUntilTimeout(10_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            FieldScreen(
                onNavObsList = {},
                onNavSampleList = {}
            )
        }
    }


}