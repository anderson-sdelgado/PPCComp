package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.HarvesterRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.HarvesterRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.HarvesterRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IHarvesterRepositoryTest {

    private val harvesterRoomDatasource = mock<HarvesterRoomDatasource>()
    private val harvesterRetrofitDatasource = mock<HarvesterRetrofitDatasource>()
    private val repository = IHarvesterRepository(
        harvesterRetrofitDatasource = harvesterRetrofitDatasource,
        harvesterRoomDatasource = harvesterRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                HarvesterRoomModel(
                    nroHarvester = 12345
                )
            )
            val entityList = listOf(
                Harvester(
                    nroHarvester = 12345
                )
            )
            whenever(
                harvesterRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IHarvesterRoomDatasource.addAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHarvesterRepository.addAll -> IHarvesterRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                HarvesterRoomModel(
                    nroHarvester = 12345,
                )
            )
            val entityList = listOf(
                Harvester(
                    nroHarvester = 12345
                )
            )
            whenever(
                harvesterRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
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
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                harvesterRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IHarvesterRoomDatasource.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHarvesterRepository.deleteAll -> IHarvesterRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                harvesterRoomDatasource.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
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
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                harvesterRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IHarvesterRetrofitDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHarvesterRepository.listAll -> IHarvesterRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                HarvesterRetrofitModel(
                    nroHarvester = 12345
                ),
                HarvesterRetrofitModel(
                    nroHarvester = 67890
                )
            )
            val entityList = listOf(
                Harvester(
                    nroHarvester = 12345
                ),
                Harvester(
                    nroHarvester = 67890
                )
            )
            whenever(
                harvesterRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `checkNroHarvester - Check return failure if have error in HarvesterDatasource checkNroHarvester`() =
        runTest {
            whenever(
                harvesterRoomDatasource.checkNroHarvester(100)
            ).thenReturn(
                resultFailure(
                    "IHarvesterDatasource.checkNroHarvester",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkNroHarvester(100)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHarvesterRepository.checkNroHarvester -> IHarvesterDatasource.checkNroHarvester"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkNroHarvester - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                harvesterRoomDatasource.checkNroHarvester(100)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkNroHarvester(100)
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