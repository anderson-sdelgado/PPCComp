package br.com.usinasantafe.ppc.presenter.view.header.auditor

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.CheckColab
import br.com.usinasantafe.ppc.domain.usecases.header.SetAuditorHeader
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
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AuditorViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val updateTableColab = mock<UpdateTableColab>()
    private val setAuditorHeader = mock<SetAuditorHeader>()
    private val checkColab = mock<CheckColab>()
    private val viewModel = AuditorViewModel(
        updateTableColab = updateTableColab,
        setAuditorHeader = setAuditorHeader,
        checkColab = checkColab
    )

    @Test
    fun `setTextField - Check digit reg`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.regAuditor,
                "19759"
            )
        }

    @Test
    fun `setTextField - Check clean digit`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("7", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            viewModel.setTextField("", TypeButton.CLEAN)
            viewModel.setTextField("", TypeButton.CLEAN)
            assertEquals(
                viewModel.uiState.value.regAuditor,
                "197"
            )
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
                "AuditorViewModel.setTextField.OK -> Field Empty!"
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
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanColab -> java.lang.NullPointerException",
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
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                AuditorState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "AuditorViewModel.updateAllDatabase -> ICleanColab -> java.lang.NullPointerException",
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
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_colab",
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
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                AuditorState(
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
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        errors = Errors.UPDATE,
                        flagDialog = true,
                        flagFailure = true,
                        failure = "ICleanColab -> java.lang.NullPointerException",
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
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                AuditorState(
                    errors = Errors.UPDATE,
                    flagDialog = true,
                    flagFailure = true,
                    failure = "AuditorViewModel.updateAllDatabase -> ICleanColab -> java.lang.NullPointerException",
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
                "AuditorViewModel.updateAllDatabase -> ICleanColab -> java.lang.NullPointerException"
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
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(1f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.CLEAN,
                        tableUpdate = "tb_colab",
                        currentProgress = percentage(2f, 4f)
                    ),
                    ResultUpdateModel(
                        flagProgress = true,
                        levelUpdate = LevelUpdate.SAVE,
                        tableUpdate = "tb_colab",
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
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.RECOVERY,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(1f, 4f)
                )
            )
            assertEquals(
                result[1],
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.CLEAN,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(2f, 4f)
                )
            )
            assertEquals(
                result[2],
                AuditorState(
                    flagProgress = true,
                    levelUpdate = LevelUpdate.SAVE,
                    tableUpdate = "tb_colab",
                    currentProgress = percentage(3f, 4f)
                )
            )
            assertEquals(
                result[3],
                AuditorState(
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
                "AuditorViewModel.setRegAuditor -> CheckColab -> java.lang.Exception"
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
            assertEquals(
                viewModel.uiState.value.posAuditor,
                1
            )
        }

    @Test
    fun `setTextField - Check return false if regColab is invalid`() =
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
                "AuditorViewModel.setRegAuditor -> RegAuditor Invalid!"
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
            assertEquals(
                viewModel.uiState.value.posAuditor,
                1
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in SetRegAuditor`() =
        runTest {
            whenever(
                checkColab("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setAuditorHeader(
                    pos = 1,
                    regAuditor = "19759"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetRegAuditor",
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
                "AuditorViewModel.setRegAuditor -> SetRegAuditor -> java.lang.Exception"
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
            assertEquals(
                viewModel.uiState.value.posAuditor,
                1
            )
        }

    @Test
    fun `setTextField - Check return true if SetAuditor execute successfully and pos is less than 3`() =
        runTest {
            whenever(
                checkColab("19759")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setAuditorHeader(
                    pos = 1,
                    regAuditor = "19759"
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
                viewModel.uiState.value.posAuditor,
                2
            )
        }

    @Test
    fun `setTextField - Check return true if SetAuditor execute successfully and pos is 2`() =
        runTest {
            viewModel.next()
            viewModel.setTextField("", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

    @Test
    fun `ret - Check alter posAuditor`() =
        runTest {
            viewModel.next()
            viewModel.ret()
            assertEquals(
                viewModel.uiState.value.posAuditor,
                1
            )
        }

}