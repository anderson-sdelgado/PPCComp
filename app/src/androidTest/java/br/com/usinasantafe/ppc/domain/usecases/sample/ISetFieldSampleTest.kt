package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.utils.Field
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetFieldSampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetWeightSample

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_alter_data_in_shared_preferences() =
        runTest {
            val resultBefore = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.piece,
                null
            )
            val result = usecase(
                field = Field.PIECE,
                value = "1.0"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.piece,
                1.0
            )
        }

    @Test
    fun check_clean_and_alter_data_in_shared_preferences() =
        runTest {
            val resultSetBefore = usecase(
                field = Field.PIECE,
                value = "1.0"
            )
            assertEquals(
                resultSetBefore.isSuccess,
                true
            )
            assertEquals(
                resultSetBefore.getOrNull()!!,
                true
            )
            val resultGetBefore = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.piece,
                1.0
            )
            val resultSetAfter = usecase(
                field = Field.TARE,
                value = "2.0"
            )
            assertEquals(
                resultSetAfter.isSuccess,
                true
            )
            assertEquals(
                resultSetAfter.getOrNull()!!,
                true
            )
            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.piece,
                null
            )
            assertEquals(
                modelAfter.tare,
                2.0
            )
        }


}