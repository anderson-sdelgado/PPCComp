package br.com.usinasantafe.ppc.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.HeaderSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IHeaderSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IHeaderSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setAuditor - Check alter data in shared preferences`() =
        runTest {
            val resultGet = datasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val modelBefore = resultGet.getOrNull()!!
            assertEquals(
                modelBefore.regAuditor1,
                null
            )
            assertEquals(
                modelBefore.regAuditor2,
                null
            )
            assertEquals(
                modelBefore.regAuditor3,
                null
            )
            val result = datasource.setAuditor(
                pos = 1,
                regAuditor = 12345
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.regAuditor1,
                12345L
            )
            assertEquals(
                modelAfter.regAuditor2,
                null
            )
            assertEquals(
                modelAfter.regAuditor3,
                null
            )
        }

    @Test
    fun `setAuditor - Check alter data in shared preferences and auditor is 2`() =
        runTest {
            datasource.save(
                HeaderSharedPreferencesModel(
                    regAuditor1 = 12345L
                )
            )
            val resultGet = datasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val modelBefore = resultGet.getOrNull()!!
            assertEquals(
                modelBefore.regAuditor1,
                12345L
            )
            assertEquals(
                modelBefore.regAuditor2,
                null
            )
            assertEquals(
                modelBefore.regAuditor3,
                null
            )
            val result = datasource.setAuditor(
                pos = 2,
                regAuditor = 19759
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.regAuditor1,
                12345L
            )
            assertEquals(
                modelAfter.regAuditor2,
                19759L
            )
            assertEquals(
                modelAfter.regAuditor3,
                null
            )
        }

}