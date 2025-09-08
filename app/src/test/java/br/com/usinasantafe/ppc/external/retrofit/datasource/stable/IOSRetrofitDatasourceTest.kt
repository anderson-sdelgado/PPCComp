package br.com.usinasantafe.ppc.external.retrofit.datasource.stable

import br.com.usinasantafe.ppc.di.provider.ProviderModuleTest.provideRetrofitTest
import br.com.usinasantafe.ppc.external.retrofit.api.stable.OSApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test

class IOSRetrofitDatasourceTest {

    private val resultOSFailure = """
          {"nroOS":1a,"idSection":1}
    """.trimIndent()

    private val resultOS = """
          {"nroOS":1,"idSection":1}
    """.trimIndent()

    @Test
    fun `getByNro - Check failure return if token is invalid`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody("{ error : Authorization header is missing }")
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.getByNroOS(
                token = "TOKEN",
                nroOS = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 4 path \$."
            )
            server.shutdown()
        }

    @Test
    fun `getByNro - Check return failure if have Error 404`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setResponseCode(404)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.getByNroOS(
                token = "TOKEN",
                nroOS = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException",
            )
            server.shutdown()
        }

    @Test
    fun `getByNro - Check failure return if web service returns data incorrect`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOSFailure)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.getByNroOS(
                token = "TOKEN",
                nroOS = 1
            )
            assertEquals(
                true,
                result.isFailure
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 10 path \$.nroOS"
            )
            server.shutdown()
        }

    @Test
    fun `getByNro - Check correct return if web service returns data correctly`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(
                MockResponse().setBody(resultOS)
            )
            val retrofit = provideRetrofitTest(
                server.url("").toString()
            )
            val service = retrofit.create(OSApi::class.java)
            val datasource = IOSRetrofitDatasource(service)
            val result = datasource.getByNroOS(
                token = "TOKEN",
                nroOS = 1
            )
            assertEquals(
                true,
                result.isSuccess
            )
            val data = result.getOrNull()!!
            assertEquals(
                data.nroOS,
                1
            )
            assertEquals(
                data.idSection,
                1
            )
            server.shutdown()
        }
}