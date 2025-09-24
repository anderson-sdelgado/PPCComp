package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class ISetObsSampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetObsSample

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_alter_data_in_shared_preferences() =
        runTest {
            val result = usecase(
                stone = false,
                treeStump = false,
                weed = true,
                anthill = true
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
                modelAfter.stone,
                false
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                true
            )
            assertEquals(
                modelAfter.anthill,
                true
            )
        }

}