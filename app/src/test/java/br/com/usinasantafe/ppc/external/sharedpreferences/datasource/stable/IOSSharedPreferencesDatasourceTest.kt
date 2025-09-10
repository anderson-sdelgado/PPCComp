package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.stable

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IOSSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: IOSSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = IOSSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Check clean data`() =
        runTest {
            val resultAdd = datasource.save(
                OSSharedPreferencesModel(
                    nroOS = 123456,
                    idSection = 1
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            assertEquals(
                resultAdd.getOrNull()!!,
                true
            )
            val resultGetBefore = datasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                123456
            )
            assertEquals(
                modelBefore.idSection,
                1
            )
            val result = datasource.clean()
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
            val modelAfter = resultGetAfter.getOrNull()
            assertEquals(
                modelAfter,
                null
            )
        }

    @Test
    fun `checkHas - Check return false if not have data in os shared preferences`() =
        runTest {
            val result = datasource.checkHas()
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
    fun `checkHas - Check return true if have data in os shared preferences`() =
        runTest {
            val resultAdd = datasource.save(
                OSSharedPreferencesModel(
                    nroOS = 123456,
                    idSection = 1
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            assertEquals(
                resultAdd.getOrNull()!!,
                true
            )
            val result = datasource.checkHas()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun `checkNroAndIdSection - Check return false if data is different`() =
        runTest {
            val resultAdd = datasource.save(
                OSSharedPreferencesModel(
                    nroOS = 123456,
                    idSection = 1
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            assertEquals(
                resultAdd.getOrNull()!!,
                true
            )
            val result = datasource.checkNroAndIdSection(
                nroOS = 123457,
                idSection = 2
            )
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
    fun `checkNroAndIdSection - Check return true if data is equal`() =
        runTest {
            val resultAdd = datasource.save(
                OSSharedPreferencesModel(
                    nroOS = 123456,
                    idSection = 1
                )
            )
            assertEquals(
                resultAdd.isSuccess,
                true
            )
            assertEquals(
                resultAdd.getOrNull()!!,
                true
            )
            val result = datasource.checkNroAndIdSection(
                nroOS = 123456,
                idSection = 1
            )
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