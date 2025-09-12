package br.com.usinasantafe.ppc.presenter.view.header.turn

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.SetTurnHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class TurnViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setTurnHeader = mock<SetTurnHeader>()
    private val viewModel = TurnViewModel(
        setTurnHeader = setTurnHeader
    )

    @Test
    fun `setTurn - Check return failure if have error in SetTurnHeader`() =
        runTest {
            whenever(
                setTurnHeader(1)
            ).thenReturn(
                resultFailure(
                    context = "SetTurnHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTurn(1)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "TurnViewModel.setTurn -> SetTurnHeader -> java.lang.Exception"
            )
        }
    
    @Test
    fun `setTurn - Check return true if SetTurnHeader execute successfully`() =
        runTest {
            whenever(
                setTurnHeader(1)
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTurn(1)
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}