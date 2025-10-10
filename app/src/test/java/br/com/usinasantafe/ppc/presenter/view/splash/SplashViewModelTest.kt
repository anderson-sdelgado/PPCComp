package br.com.usinasantafe.ppc.presenter.view.splash

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.config.CheckUpdateApp
import br.com.usinasantafe.ppc.domain.usecases.config.UpdateApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkUpdateApp = mock<CheckUpdateApp>()
    private val updateApp = mock<UpdateApp>()

    private val viewModel = SplashViewModel(
        checkUpdateApp = checkUpdateApp,
        updateApp = updateApp
    )

    @Test
    fun `startApp - Check return failure if have error in CheckUpdateApp`() =
        runTest {
            whenever(
                checkUpdateApp("1.00")
            ).thenReturn(
                resultFailure(
                    context = "CheckUpdateApp",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.startApp("1.00")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SplashViewModel.startApp -> CheckUpdateApp -> java.lang.Exception"
            )
        }

    @Test
    fun `startApp - Check return correct if CheckUpdateApp return false`() =
        runTest {
            whenever(
                checkUpdateApp("1.00")
            ).thenReturn(
                Result.success(false)
            )
            viewModel.startApp("1.00")
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagUpdate,
                false
            )
        }

    @Test
    fun `startApp - Check return failure if have error in UpdateApp`() =
        runTest {
            whenever(
                checkUpdateApp("1.00")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                updateApp()
            ).thenReturn(
                flowOf(
                resultFailure(
                        context = "UpdateApp",
                        message = "-",
                        cause = Exception()
                    )
                )
            )
            viewModel.startApp("1.00")
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SplashViewModel.startApp -> UpdateApp -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagUpdate,
                false
            )
        }

    @Test
    fun `startApp - Check return correct if Update execute successfully`() =
        runTest {
            whenever(
                checkUpdateApp("1.00")
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                updateApp()
            ).thenReturn(
                flowOf(
                    Result.success(0.3f)
                )
            )
            viewModel.startApp("1.00")
            assertEquals(
                viewModel.uiState.value.flagUpdate,
                true
            )
            assertEquals(
                viewModel.uiState.value.currentProgress,
                0.3f
            )
        }
}