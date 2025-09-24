package br.com.usinasantafe.ppc.presenter.view.sample.field

import android.annotation.SuppressLint
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.ppc.HiltTestActivity
import br.com.usinasantafe.ppc.domain.usecases.sample.CheckWeightRelationTare
import br.com.usinasantafe.ppc.domain.usecases.sample.SetWeightSample
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.presenter.Args.CHECK_OPEN_SAMPLE_ARGS
import br.com.usinasantafe.ppc.presenter.theme.TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE
import br.com.usinasantafe.ppc.presenter.theme.TAG_BUTTON_YES_ALERT_DIALOG_CHECK
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.waitUntilTimeout
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class FieldScreenTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Inject
    lateinit var checkWeightRelationTare: CheckWeightRelationTare

    @Inject
    lateinit var setWeightSample: SetWeightSample

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
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("5").performClick()

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_add_tare() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultTare = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultTare.isSuccess,
                true
            )
            val modelTare = resultTare.getOrNull()!!
            assertEquals(
                modelTare.tare,
                1.045
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_msg_value_if_minor_tare_and_zero() =
        runTest {

            hiltRule.inject()

            setContent()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("4").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultTare = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultTare.isSuccess,
                true
            )
            val modelTare = resultTare.getOrNull()!!
            assertEquals(
                modelTare.tare,
                2.045
            )

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("2").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_OK_ALERT_DIALOG_SIMPLE)
                .performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("APAGAR").performClick()
            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            val resultStalk = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultStalk.isSuccess,
                true
            )
            val modelStalk = resultStalk.getOrNull()!!
            assertEquals(
                modelStalk.tare,
                2.045
            )
            assertEquals(
                modelStalk.stalk,
                3.105
            )
            assertEquals(
                modelStalk.wholeCane,
                null
            )

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("1").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("5").performClick()
            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("OK").performClick()

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithTag(TAG_BUTTON_YES_ALERT_DIALOG_CHECK)
                .performClick()

            composeTestRule.waitUntilTimeout()

            val resultWholeCane = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultStalk.isSuccess,
                true
            )
            val modelWholeCane = resultWholeCane.getOrNull()!!
            assertEquals(
                modelWholeCane.tare,
                2.045
            )
            assertEquals(
                modelWholeCane.stalk,
                3.105
            )
            assertEquals(
                modelWholeCane.wholeCane,
                null
            )

            composeTestRule.waitUntilTimeout()

            composeTestRule.onNodeWithText("3").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("0").performClick()
            composeTestRule.onNodeWithText("OK").performClick()
            composeTestRule.waitUntilTimeout()

            val resultStump = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultStump.isSuccess,
                true
            )
            val modelStump = resultStump.getOrNull()!!
            assertEquals(
                modelStump.tare,
                2.045
            )
            assertEquals(
                modelStump.stalk,
                3.105
            )
            assertEquals(
                modelStump.wholeCane,
                null
            )
            assertEquals(
                modelStump.stump,
                3.0
            )

            composeTestRule.waitUntilTimeout(10_000)

        }

    @Test
    fun check_return_to_obs() =
        runTest {

            hiltRule.inject()

            setContent(false)

            composeTestRule.waitUntilTimeout()

            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            composeTestRule.waitUntilTimeout(10_000)
        }

    @SuppressLint("ViewModelConstructorInComposable")
    private fun setContent(
        check: Boolean = true
    ){
        composeTestRule.setContent {
            FieldScreen(
                viewModel = FieldViewModel(
                    saveStateHandle = SavedStateHandle(
                        mapOf(
                            CHECK_OPEN_SAMPLE_ARGS to check
                        )
                    ),
                    checkWeightRelationTare = checkWeightRelationTare,
                    setWeightSample = setWeightSample
                ),
                onNavObsList = {},
                onNavSampleList = {}
            )
        }
    }


}