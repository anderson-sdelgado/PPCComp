package br.com.usinasantafe.ppc.domain.usecases.update

import br.com.usinasantafe.ppc.domain.entities.stable.Harvester
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.HarvesterRepository
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

class IUpdateTableHarvesterTest {

    private val getToken = mock<GetToken>()
    private val harvesterRepository = mock<HarvesterRepository>()
    private val usecase = IUpdateTableHarvester(
        getToken = getToken,
        harvesterRepository = harvesterRepository
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
                    tableUpdate = "tb_harvester",
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
                    failure = "IUpdateTableHarvester -> GetToken -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in HarvesterRepository listAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                harvesterRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                resultFailure(
                    "IHarvesterRepository.listAll",
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
                    tableUpdate = "tb_harvester",
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
                    failure = "IUpdateTableHarvester -> IHarvesterRepository.listAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in HarvesterRepository deleteAll`() =
        runTest {
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                harvesterRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(
                    listOf(
                        Harvester(
                            numberHarvester = 1
                        )
                    )
                )
            )
            whenever(
                harvesterRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IHarvesterRepository.deleteAll",
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
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_harvester",
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
                    failure = "IUpdateTableHarvester -> IHarvesterRepository.deleteAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return failure if have error in HarvesterRepository addAll`() =
        runTest {
            val entityList = listOf(
                Harvester(
                    numberHarvester = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                harvesterRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                harvesterRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                harvesterRepository.addAll(entityList)
            ).thenReturn(
                resultFailure(
                    "IHarvesterRepository.addAll",
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
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_harvester",
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
                    failure = "IUpdateTableHarvester -> IHarvesterRepository.addAll -> java.lang.Exception",
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `Check return correct if process execute successfully`() =
        runTest {
            val entityList = listOf(
                Harvester(
                    numberHarvester = 1
                )
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success("token")
            )
            whenever(
                harvesterRepository.listAll(
                    token = "token"
                )
            ).thenReturn(
                Result.success(entityList)
            )
            whenever(
                harvesterRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                harvesterRepository.addAll(entityList)
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
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(1f, 1f, 7f)
                )
            )
            assertEquals(
                list[1],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(2f, 1f, 7f)
                )
            )
            assertEquals(
                list[2],
                ResultUpdateModel(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_harvester",
                    currentProgress = updatePercentage(3f, 1f, 7f)
                )
            )
        }

}