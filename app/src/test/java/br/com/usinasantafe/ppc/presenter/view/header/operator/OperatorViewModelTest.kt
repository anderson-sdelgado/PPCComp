package br.com.usinasantafe.ppc.presenter.view.header.operator

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.CheckColab
import br.com.usinasantafe.ppc.domain.usecases.header.SetOperatorHeader
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableColab
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
class OperatorViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkColab = mock<CheckColab>()
    private val updateTableColab = mock<UpdateTableColab>()
    private val setOperatorHeader = mock<SetOperatorHeader>()
    private val viewModel = OperatorViewModel(
        checkColab = checkColab,
        updateTableColab = updateTableColab,
        setOperatorHeader = setOperatorHeader
    )

    @Test
    fun `setTextField - Check add char`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC
            )
            assertEquals(
                viewModel.uiState.value.regOperator,
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
            assertEquals(viewModel.uiState.value.regOperator, "121")
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
                "OperatorViewModel.setTextField.OK -> Field Empty!"
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
    fun `updateAllDatabase - Check return failure if have error in UpdateTableColab`() =
        runTest {
            whenever(
                updateTableColab(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanOperator -> java.lang.NullPointerException",
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
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "OperatorViewModel.updateAllDatabase -> ICleanOperator -> java.lang.NullPointerException",
                    currentProgress = 1f,
                    levelUpdate = null,
                )
            )
        }

    @Test
    fun `updateAllDatabase - Check return success if update execute successfully`() =
        runTest {
            whenever(
                updateTableColab(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_operator",
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
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                OperatorState(
                    flagDialog = true,
                    flagProgress = false,
                    flagFailure = false,
                    levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                    currentProgress = 1f,
                )
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in UpdateTableColab`() =
        runTest {
            whenever(
                updateTableColab(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanOperator -> java.lang.NullPointerException",
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
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "OperatorViewModel.updateAllDatabase -> ICleanOperator -> java.lang.NullPointerException",
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
                "OperatorViewModel.updateAllDatabase -> ICleanOperator -> java.lang.NullPointerException"
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
                updateTableColab(
                    sizeAll = 4f,
                    count = 1f
                )
            ).thenReturn(
                flowOf(
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.RECOVERY,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_operator",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_operator",
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
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                OperatorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_operator",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                OperatorState(
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
    fun `setTextField - Check return failure if have error in CheckColab`() =
        runTest {
            whenever(
                checkColab("19759")
            ).thenReturn(
                resultFailure(
                    context = "CheckColab",
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
                "OperatorViewModel.setNroOperator -> CheckColab -> java.lang.Exception"
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
    fun `setTextField - Check return false if regOperator is invalid`() =
        runTest {
            whenever(
                checkColab("19759")
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
                "OperatorViewModel.setNroOperator -> NroOperator Invalid!"
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
    fun `setTextField - Check return failure if have error in SetNroOperator`() =
        runTest {
            whenever(
                checkColab("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setOperatorHeader(
                    regOperator = "19759"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNroOperator",
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
                "OperatorViewModel.setNroOperator -> SetNroOperator -> java.lang.Exception"
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
    fun `setTextField - Check return success if SetNroOperator execute successfully`() =
        runTest {
            whenever(
                checkColab("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setOperatorHeader(
                    regOperator = "19759"
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