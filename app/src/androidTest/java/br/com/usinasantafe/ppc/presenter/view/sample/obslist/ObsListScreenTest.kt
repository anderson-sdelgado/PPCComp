package br.com.usinasantafe.ppc.presenter.view.sample.obslist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.Test

@HiltAndroidTest
class ObsListScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Test
    fun check_open_screen() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout(30_000)

        }

    private fun setContent(){
        composeTestRule.setContent {
            ObsListScreen(
                onNavField = {},
                onNavSampleList = {},
                onNavObsSubList = {}
            )
        }
    }


}