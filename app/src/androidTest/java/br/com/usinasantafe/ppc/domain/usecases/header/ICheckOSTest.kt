package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.di.provider.BaseUrlModuleTest
import br.com.usinasantafe.ppc.di.provider.IFakeCheckNetwork
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.stable.IOSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.presenter.model.ResultCheckDataWebServiceModel
import br.com.usinasantafe.ppc.utils.CheckNetwork
import br.com.usinasantafe.ppc.utils.FlagUpdate
import br.com.usinasantafe.ppc.utils.StatusCon
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

@HiltAndroidTest
class ICheckOSTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckOS

    @Inject
    lateinit var configSharedPreferencesDatasource: ConfigSharedPreferencesDatasource

    @Inject
    lateinit var osSharedPreferencesDatasource: IOSSharedPreferencesDatasource

    @Inject
    lateinit var checkNetwork: CheckNetwork

    private val resultOSFailure = """
          {"nroOS":2a,"idSection":12}
    """.trimIndent()

    private val resultOSInvalid = """
          {"nroOS":0,"idSection":0}
    """.trimIndent()

    private val resultOS = """
          {"nroOS":20,"idSection":2}
    """.trimIndent()

    @Test
    fun check_failure_return_if_not_have_data_in_config_shared_preferences() =
        runTest {
            hiltRule.inject()
            val result = usecase("1")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_true_return_and_os_table_is_empty_if_web_return_failure() =
        runTest {
            hiltRule.inject()

            initialRegister()

            val resultGetBefore = osSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                1
            )
            assertEquals(
                modelBefore.idSection,
                1
            )

            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getByNroOS -> IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080"
            )

            val resultGetAfter = osSharedPreferencesDatasource.get()
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
    fun check_failure_return_if_token_is_invalid() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()
            initialRegister()

            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getByNroOS -> IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 4 path \$."
            )

        }

    @Test
    fun check_failure_return_if_have_error_404() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()
            initialRegister()

            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getByNroOS -> IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException",
            )

        }

    @Test
    fun check_failure_return_if_web_service_returns_data_incorrect() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSFailure)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()
            initialRegister()

            val result = usecase("2")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getByNroOS -> IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 10 path \$.nroOS",
            )

        }

    @Test
    fun check_status_connection_slow_return_if_web_service_slow_return() =
        runTest(
            timeout = 1.minutes
        ) {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse()
                    .setBody(resultOSInvalid)
                    .setBodyDelay(12, TimeUnit.SECONDS)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()
            initialRegister()

            val result = usecase("2")
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.statusCon,
                StatusCon.SLOW
            )
            assertEquals(
                model.check,
                null
            )

        }

    @Test
    fun check_status_without_connection_return_if_not_have_connection() =
        runTest {

            hiltRule.inject()
            initialRegister()

            (checkNetwork as IFakeCheckNetwork).setConnected(false)

            val result = usecase("2")
            assertEquals(
                result.isSuccess,
                true
            )
            val model = result.getOrNull()!!
            assertEquals(
                model.statusCon,
                StatusCon.WITHOUT
            )
            assertEquals(
                model.check,
                null
            )

        }

    @Test
    fun check_data_return_if_web_service_returns_nroOS_invalid() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSInvalid)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()
            initialRegister()

            val resultGetBefore = osSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                1
            )
            assertEquals(
                modelBefore.idSection,
                1
            )

            val result = usecase("2")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = false
                )
            )

            val resultGetAfter = osSharedPreferencesDatasource.get()
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
    fun check_data_return_if_web_service_returns_data_correct() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOS)
            )
            BaseUrlModuleTest.url = server.url("/").toString()

            hiltRule.inject()

            initialRegister()

            val resultGetBefore = osSharedPreferencesDatasource.get()
            assertEquals(
                resultGetBefore.isSuccess,
                true
            )
            val modelBefore = resultGetBefore.getOrNull()!!
            assertEquals(
                modelBefore.nroOS,
                1
            )
            assertEquals(
                modelBefore.idSection,
                1
            )

            val result = usecase("2")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = true
                )
            )

            val resultGetAfter = osSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.nroOS,
                20
            )
            assertEquals(
                modelAfter.idSection,
                2
            )
        }

    private suspend fun initialRegister() {

        configSharedPreferencesDatasource.save(
            ConfigSharedPreferencesModel(
                number = 16997417840,
                password = "12345",
                idServ = 1,
                version = "1.0",
                flagUpdate = FlagUpdate.UPDATED
            )
        )

        osSharedPreferencesDatasource.save(
            OSSharedPreferencesModel(
                nroOS = 1,
                idSection = 1
            )
        )

    }

}