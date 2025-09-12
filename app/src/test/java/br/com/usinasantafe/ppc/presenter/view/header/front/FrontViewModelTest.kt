package br.com.usinasantafe.ppc.presenter.view.header.front

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.SetFrontHeader
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FrontViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setFrontHeader = mock<SetFrontHeader>()
    private val viewModel = FrontViewModel(
        setFrontHeader = setFrontHeader
    )

    @Test
    fun `setTextField - Check add char`() =
        runTest {
            viewModel.setTextField("1", TypeButton.NUMERIC
            )
            assertEquals(
                viewModel.uiState.value.nroFront,
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
            assertEquals(viewModel.uiState.value.nroFront, "121")
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
                "FrontViewModel.setTextField.OK -> Field Empty!"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.FIELD_EMPTY
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in SetNroFront`() =
        runTest {
            whenever(
                setFrontHeader(
                    nroFront = "19759"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetNroFront",
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
                "FrontViewModel.setNroFront -> SetNroFront -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setTextField - Check return success if SetNroPlot execute successfully`() =
        runTest {
            whenever(
                setFrontHeader(
                    nroFront = "19759"
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