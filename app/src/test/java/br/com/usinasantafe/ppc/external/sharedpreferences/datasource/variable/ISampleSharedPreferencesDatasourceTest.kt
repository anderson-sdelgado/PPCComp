package br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.Field
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ISampleSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource: ISampleSharedPreferencesDatasource

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("test", Context.MODE_PRIVATE)
        datasource = ISampleSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `setValue - Check alter data in shared preferences in TARE field`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.tare,
                null
            )
            val result = datasource.setValue(
                field = Field.TARE,
                value = 1.0
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
                modelAfter.tare,
                1.0
            )
        }

    @Test
    fun `setValue - Check alter data in shared preferences in PIECE field`() =
        runTest {
            val resultBefore = datasource.get()
            assertEquals(
                resultBefore.isSuccess,
                true
            )
            val modelBefore = resultBefore.getOrNull()!!
            assertEquals(
                modelBefore.piece,
                null
            )
            val result = datasource.setValue(
                field = Field.PIECE,
                value = 1.0
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
                modelAfter.piece,
                1.0
            )
        }

    @Test
    fun `clean - Check clean data in shared preferences`() =
        runTest {
            val resultSet = datasource.setValue(
                field = Field.PIECE,
                value = 1.0
            )
            assertEquals(
                resultSet.isSuccess,
                true
            )
            assertEquals(
                resultSet.getOrNull()!!,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.piece,
                1.0
            )
            val resultClean = datasource.clean()
            assertEquals(
                resultClean.isSuccess,
                true
            )
            assertEquals(
                resultClean.getOrNull()!!,
                true
            )
            val resultGetAfterClean = datasource.get()
            assertEquals(
                resultGetAfterClean.isSuccess,
                true
            )
            val modelAfterClean = resultGetAfterClean.getOrNull()!!
            assertEquals(
                modelAfterClean.piece,
                null
            )
        }

    @Test
    fun `getTare - Check return failure if not have data in shared preferences`() =
        runTest {
            val result = datasource.getTare()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISampleSharedPreferencesDatasource.getTare"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun `getTare - Check return data`() =
        runTest {
            datasource.save(
                SampleSharedPreferencesModel(
                    tare = 1.0,
                )
            )
            val result = datasource.getTare()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                1.0,
                0.0
            )
        }

    @Test
    fun `setObs - Check alter data obs`() =
        runTest {
            val result = datasource.setObs(
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
            val resultGetAfter = datasource.get()
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

    @Test
    fun `setSubObs - Check alter data sub obs`() =
        runTest {
            val result = datasource.setSubObs(
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = false,
                silkGrass = true
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
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                true
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                false
            )
            assertEquals(
                modelAfter.silkGrass,
                true
            )
        }

}