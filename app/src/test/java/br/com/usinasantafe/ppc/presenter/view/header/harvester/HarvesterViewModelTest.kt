package br.com.usinasantafe.ppc.presenter.view.header.harvester

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.CheckHarvester
import br.com.usinasantafe.ppc.domain.usecases.header.SetHarvesterHeader
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableHarvester
import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.LevelUpdate
import br.com.usinasantafe.ppc.utils.TypeButton
import br.com.usinasantafe.ppc.utils.percentage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class HarvesterViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkHarvester = mock<CheckHarvester>()
    private val updateTableHarvester = mock<UpdateTableHarvester>()
    private val setHarvesterHeader = mock<SetHarvesterHeader>()
    private val viewModel = HarvesterViewModel(
        checkHarvester = checkHarvester,
        updateTableHarvester = updateTableHarvester,
        setHarvesterHeader = setHarvesterHeader
    )

    @Test
    fun `setTextField - Check add char`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC
            )
            assertEquals(
                viewModel.uiState.value.nroHarvester,
                "1"
            )
        }

    @Test
    fun `setTextField - Check remover char`()  =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("3", TypeButton.NUMERIC)
            viewModel.setTextField("4", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            assertEquals(viewModel.uiState.value.nroHarvester, "121")
        }

    @Test
    fun `setTextField - Check return failure if field is empty`() =
        runTest {
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HarvesterViewModel.setTextField.OK -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELD_EMPTY
            )
        }

    @Test
    fun `updateAllDatabase - Check return failure if have error in UpdateTableHarvester`() =
        runTest {
            whenever(
                updateTableHarvester(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanHarvester -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null,
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                HarvesterState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "HarvesterViewModel.updateAllDatabase -> ICleanHarvester -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun `updateAllDatabase - Check return success if update execute successfully`() =
        runTest {
            whenever(
                updateTableHarvester(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                result[0],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                HarvesterState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in UpdateTableHarvester`() =
        runTest {
            whenever(
                updateTableHarvester(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanHarvester -> java.lang.NullPointerException",
                        currentProgress = 1f,
                        levelUpdate = null,
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                2
            )
            assertEquals(
                result[0],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                HarvesterState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "HarvesterViewModel.updateAllDatabase -> ICleanHarvester -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
            viewModel.setTextField("", TypeButton.UPDATE)
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.UPDATE
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HarvesterViewModel.updateAllDatabase -> ICleanHarvester -> java.lang.NullPointerException"
            )
            assertEquals(
                viewModel.uiState.value.currentProgress,
                1f
            )
            assertEquals(
                viewModel.uiState.value.levelUpdate,
                null
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `setTextField - Check return success if update execute successfully`() =
        runTest {
            whenever(
                updateTableHarvester(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_harvester",
                        currentProgress = percentage(3f, 4f)
                    ),
                )
            )
            val result = viewModel.updateAllDatabase().toList()
            assertEquals(
                result.count(),
                4
            )
            assertEquals(
                result[0],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                HarvesterState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_harvester",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                HarvesterState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
            viewModel.setTextField("", TypeButton.UPDATE)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                false
            )
            assertEquals(
                viewModel.uiState.value.levelUpdate,
                LevelUpdate.FINISH_UPDATE_COMPLETED
            )
            assertEquals(
                viewModel.uiState.value.currentProgress,
                1f
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in CheckHarvester`() =
        runTest {
            whenever(
                checkHarvester("19759")
            ).thenReturn(
                resultFailure(
                    context = "CheckHarvester",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HarvesterViewModel.setNroHarvester -> CheckHarvester -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return false if nroHarvester is invalid`() =
        runTest {
            whenever(
                checkHarvester("19759")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HarvesterViewModel.setNroHarvester -> NroHarvester Invalid!"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in SetNroHarvester`() =
        runTest {
            whenever(
                checkHarvester("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setHarvesterHeader(
                    nroHarvester = "19759"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNroHarvester",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "HarvesterViewModel.setNroHarvester -> SetNroHarvester -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagFailure,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
        }

    @Test
    fun `setTextField - Check return success if SetNroHarvester execute successfully`() =
        runTest {
            whenever(
                checkHarvester("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setHarvesterHeader(
                    nroHarvester = "19759"
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}