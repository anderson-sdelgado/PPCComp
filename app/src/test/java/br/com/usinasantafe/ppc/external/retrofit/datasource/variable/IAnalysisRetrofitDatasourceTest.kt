package br.com.usinasantafe.ppc.external.retrofit.datasource.variable

import br.com.usinasantafe.ppc.di.provider.ProviderModuleTest.provideRetrofitTest
import br.com.usinasantafe.ppc.external.retrofit.api.variable.AnalysisApi
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.HeaderRetrofitModelOutput
import br.com.usinasantafe.ppc.infra.models.retrofit.variable.SampleRetrofitModelOutput
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import kotlin.test.Test
import kotlin.test.assertEquals

class IAnalysisRetrofitDatasourceTest {

    val retrofitModelOutputList = listOf(
        HeaderRetrofitModelOutput(
            id = 1,
            regAuditor1 = 1,
            regAuditor2 = 2,
            regAuditor3 = 3,
            date = "30/09/2025",
            nroTurn = 1,
            codSection = 1,
            nroPlot = 1,
            nroOS = 1,
            codFront = 1,
            nroHarvester = 1,
            regOperator = 1,
            dateHour = "30/09/2025 12:00",
            number = 1,
            sampleList = listOf(
                SampleRetrofitModelOutput(
                    id = 1,
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true
                )
            )
        )
    )

    @Test
    fun `send - Check return failure if have error 404`() =
        runTest {

            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setResponseCode(404))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(AnalysisApi::class.java)
            val dataSource = IAnalysisRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.NullPointerException"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if have failure Connection`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("Failure Connection BD"))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(AnalysisApi::class.java)
            val dataSource = IAnalysisRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "com.google.gson.stream.MalformedJsonException: Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return failure if sent data incorrect`() =
        runTest {
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody("""{"id":1a}"""))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(AnalysisApi::class.java)
            val dataSource = IAnalysisRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IAnalysisRetrofitDatasource.send"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause!!.toString(),
                "java.lang.IllegalStateException: Expected BEGIN_ARRAY but was BEGIN_OBJECT at line 1 column 2 path \$"
            )
            server.shutdown()
        }

    @Test
    fun `send - Check return correct if function execute successfully`() =
        runTest {

            val response = """
                [
                    {
                        "id": 1,
                        "idServ": 1,
                        "sampleList": [
                            {
                                "id": 1,
                                "idServ": 1
                            }
                        ]
                    }
                ]
            """.trimIndent()
            val server = MockWebServer()
            server.start()
            server.enqueue(MockResponse().setBody(response))
            val retrofit = provideRetrofitTest(server.url("/").toString())
            val service = retrofit.create(AnalysisApi::class.java)
            val dataSource = IAnalysisRetrofitDatasource(service)
            val result = dataSource.send(
                "token",
                retrofitModelOutputList
            )
            assertEquals(
                result.isSuccess,
                true
            )
            val headerRetrofitModelInputList = result.getOrNull()!!
            assertEquals(
                headerRetrofitModelInputList.size,
                1
            )
            val headerRetrofitModelInput = headerRetrofitModelInputList[0]
            assertEquals(
                headerRetrofitModelInput.id,
                1
            )
            assertEquals(
                headerRetrofitModelInput.idServ,
                1
            )
            assertEquals(
                headerRetrofitModelInput.sampleList.size,
                1
            )
            val sampleRetrofitModelInput = headerRetrofitModelInput.sampleList[0]
            assertEquals(
                sampleRetrofitModelInput.id,
                1
            )
            assertEquals(
                sampleRetrofitModelInput.idServ,
                1
            )
            server.shutdown()
        }
}