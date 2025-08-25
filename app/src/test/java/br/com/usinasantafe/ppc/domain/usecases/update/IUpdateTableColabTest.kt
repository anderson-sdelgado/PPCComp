package br.com.usinasantafe.ppc.domain.usecases.update

import br.com.usinasantafe.ppc.domain.entities.stable.Colab
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.ppc.domain.usecases.config.GetToken
import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.LevelUpdate
import br.com.usinasantafe.ppc.utils.updatePercentage
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class IUpdateTableColabTest {

    private val getToken = mock<GetToken>()
    private val colabRepository = mock<ColabRepository>()
    private val usecase = IUpdateTableColab(
        getToken = getToken,
        colabRepository = colabRepository
    )

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "GetToken",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableColab -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository listAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                resultFailure(
                    "IColabRepository.listAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableColab -> IColabRepository.listAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Colab(
                            regColab = 1
                        )
                    )
                )
            )
            whenever(
                colabRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IColabRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableColab -> IColabRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository addAll`() =
        runTest {
            val entityList = listOf(
                Colab(
                    regColab = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                colabRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                colabRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IColabRepository.addAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
            assertEquals(
                list[3],
                ResultUpdateModel(
                    flagProgress = true,
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "IUpdateTableColab -> IColabRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                Colab(
                    regColab = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                colabRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                colabRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                colabRepository.addAll(entityList)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase(
                sizeAll = 7f,
                count = 1f
            )
            val list = result.toList()
            assertEquals(
                result.count(),
                3
            )
            assertEquals(
                list[0],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }

}