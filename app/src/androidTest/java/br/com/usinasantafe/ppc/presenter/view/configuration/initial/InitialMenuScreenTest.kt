package br.com.usinasantafe.ppc.presenter.view.configuration.initial

import androidx.compose.ui.test.junit4.createAndroidComposeRule
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

    private fun setContent() {
        composeTestRule.setContent {
            InitialMenuScreen(
                onNavPassword = {},
                onNavHeaderList = {}
            )
        }
    }

}