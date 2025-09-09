package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Colab
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.ColabRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.ColabRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.ColabRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IColabRepositoryTest {

    private val colabRoomDatasource = mock<ColabRoomDatasource>()
    private val colabRetrofitDatasource = mock<ColabRetrofitDatasource>()
    private val repository = IColabRepository(
        colabRetrofitDatasource = colabRetrofitDatasource,
        colabRoomDatasource = colabRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                ColabRoomModel(
                    regColab = 12345L
                )
            )
            val entityList = listOf(
                Colab(
                    regColab = 12345L
                )
            )
            whenever(
                colabRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "IColabRoomDatasource.addAll",
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
                "IColabRepository.addAll -> IColabRoomDatasource.addAll"
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
                ColabRoomModel(
                    regColab = 12345L,
                )
            )
            val entityList = listOf(
                Colab(
                    regColab = 12345L
                )
            )
            whenever(
                colabRoomDatasource.addAll(roomModelList)
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
                colabRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IColabRoomDatasource.deleteAll",
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
                "IColabRepository.deleteAll -> IColabRoomDatasource.deleteAll"
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
                colabRoomDatasource.deleteAll()
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
                colabRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "IColabRetrofitDatasource.listAll",
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
                "IColabRepository.listAll -> IColabRetrofitDatasource.listAll"
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
                ColabRetrofitModel(
                    regColab = 12345L
                ),
                ColabRetrofitModel(
                    regColab = 67890L
                )
            )
            val entityList = listOf(
                Colab(
                    regColab = 12345L
                ),
                Colab(
                    regColab = 67890L
                )
            )
            whenever(
                colabRetrofitDatasource.listAll("token")
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
    fun `check - Check return failure if have error in ColabDatasource check`() =
        runTest {
            whenever(
                colabRoomDatasource.checkRegColab(12345)
            ).thenReturn(
                resultFailure(
                    "IColabDatasource.check",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkRegColab(12345)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IColabRepository.check -> IColabDatasource.check"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `check - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                colabRoomDatasource.checkRegColab(12345)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkRegColab(12345)
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