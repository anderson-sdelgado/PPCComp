package br.com.usinasantafe.ppc.presenter.view.configuration.initial

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class InitialMenuScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun check_open_screen() =
        runTest {

            setContent()

            composeTestRule.waitUntilTimeout(10_000)

        }


    @Test
    fun check_open_screen_and_check_msg_if_access_is_blocked() =
        runTest {

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APONTAMENTO")
                .performClick()

            composeTestRule.waitUntilTimeout()

        }


    private fun setContent() {
        composeTestRule.setContent {
            InitialMenuScreen(
                onNavPassword = {},
                onNavHeaderList = {}
            )
        }
    }

}