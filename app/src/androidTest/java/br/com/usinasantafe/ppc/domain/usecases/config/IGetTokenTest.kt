package br.com.usinasantafe.ppc.domain.usecases.config

import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.token
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class IGetTokenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: GetToken

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetToken"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_failure_if_config_data_internal_have_field_empty() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idServ = 1,
                    number = 1
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isFailure,
                true
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.message,
                "IGetToken"
            )
            Assert.assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_and_data_returned() =
        runTest {
            configSharedPreferencesDatasource.save(
                ConfigSharedPreferencesModel(
                    idServ = 1,
                    number = 1,
                    version = "1.00"
                )
            )
            val result = usecase()
            Assert.assertEquals(
                result.isSuccess,
                true
            )
            val token = token(
                idServ = 1,
                number = 1,
                version = "1.00"
            )
            Assert.assertEquals(
                result.getOrNull()!!,
                token
            )
        }

}