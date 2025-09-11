package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
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
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
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
            val resultAfter = datasource.get()
            assertEquals(
                resultAfter.isSuccess,
                true
            )
            val modelAfter = resultAfter.getOrNull()!!
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

    @Test
    fun `setDate - Check alter data in shared preferences`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.date,
                null
            )
            val result = datasource.setDate(
                date = Date(1756928843000)
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
                modelAfter.date,
                Date(1756928843000)
            )
        }

    @Test
    fun `setTurn - Check alter data in shared preferences`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroTurn,
                null
            )
            val result = datasource.setTurn(1)
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
                modelAfter.nroTurn,
                1
            )
        }

    @Test
    fun `setOS - Check alter data in shared preferences`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                null
            )
            val result = datasource.setOS(1)
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
                modelAfter.nroOS,
                1
            )
        }
    
    @Test
    fun `getOS - Check failure return if not have data in header shared preferences`() =
        runTest {
            val result = datasource.getOS()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderSharedPreferencesDatasource.getOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getOS - Check nroOS return if have data in header shared preferences`() =
        runTest {
            datasource.save(
                HeaderSharedPreferencesModel(
                    nroOS = 1
                )
            )
            val result = datasource.getOS()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `setSection - Check alter data in shared preferences`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.codSection,
                null
            )
            val result = datasource.setSection(1)
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
                modelAfter.codSection,
                1
            )
        }

    @Test
    fun `getSection - Check failure return if not have data in header shared preferences`() =
        runTest {
            val result = datasource.getSection()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHeaderSharedPreferencesDatasource.getSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getSection - Check codSection return if have data in header shared preferences`() =
        runTest {
            datasource.save(
                HeaderSharedPreferencesModel(
                    codSection = 1
                )
            )
            val result = datasource.getSection()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1
            )
        }

    @Test
    fun `setPlot - Check alter data in shared preferences`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroPlot,
                null
            )
            val result = datasource.setPlot(1)
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
                modelAfter.nroPlot,
                1
            )
        }

}