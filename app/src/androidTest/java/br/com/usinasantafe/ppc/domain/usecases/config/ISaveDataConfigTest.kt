package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISaveDataConfigTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SaveDataConfig

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_input_data_is_incorrect() =
        runTest {
            val result = usecase(
                number = "16997417840a",
                password = "12345",
                version = "1.00",
                idServ = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISaveDataConfig"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"16997417840a\""
            )
        }

    @Test
    fun check_return_if_not_have_data() =
        runTest {
            val resultHasBefore = configSharedPreferencesDatasource.has()
            assertEquals(
                resultHasBefore.isSuccess,
                true
            )
            assertEquals(
                resultHasBefore.getOrNull()!!,
                false
            )
        }

    @Test
    fun check_return_when_have_data() =
        runTest {
            val resultHasBefore = configSharedPreferencesDatasource.has()
            assertEquals(
                resultHasBefore.isSuccess,
                true
            )
            assertEquals(
                resultHasBefore.getOrNull()!!,
                false
            )
            val result = usecase(
                number = "16997417840",
                password = "12345",
                version = "1.00",
                idServ = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultHasAfter = configSharedPreferencesDatasource.has()
            assertEquals(
                resultHasAfter.isSuccess,
                true
            )
            assertEquals(
                resultHasAfter.getOrNull()!!,
                true
            )
            val resultGet = configSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val config = resultGet.getOrNull()!!
            assertEquals(
                config.number,
                16997417840
            )
            assertEquals(
                config.password,
                "12345"
            )
            assertEquals(
                config.version,
                "1.00"
            )
        }

}