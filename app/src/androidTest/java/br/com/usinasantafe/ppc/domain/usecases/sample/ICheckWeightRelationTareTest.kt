package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ICheckWeightRelationTareTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckWeightRelationTare

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_sample_shared_preferences_is_empty() =
        runTest {
            val result = usecase("1,020")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckWeightRelationTare -> IAnalysisRepository.getTareSample -> ISampleSharedPreferencesDatasource.getTare"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_value_digit_is_not_valid() =
        runTest {
            sampleSharedPreferencesDatasource.save(
                SampleSharedPreferencesModel(
                    tare = 1.0,
                )
            )
            val result = usecase("1,020a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckWeightRelationTare"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"1.020a\""
            )
        }

    @Test
    fun check_return_false_if_the_entered_value_is_less_than_the_tare() =
        runTest {
            sampleSharedPreferencesDatasource.save(
                SampleSharedPreferencesModel(
                    tare = 1.500,
                )
            )
            val result = usecase("1,020")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_true_if_the_entered_value_is_greater_than_or_equal_to_the_tare() =
        runTest {
            sampleSharedPreferencesDatasource.save(
                SampleSharedPreferencesModel(
                    tare = 1.000,
                )
            )
            val result = usecase("1,020")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}