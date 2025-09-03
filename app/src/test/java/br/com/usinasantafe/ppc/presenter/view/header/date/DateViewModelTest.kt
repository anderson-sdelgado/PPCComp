package br.com.usinasantafe.ppc.presenter.view.header.date

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.flow.SetDateHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.util.Date
import kotlin.test.Test

@ExperimentalCoroutinesApi
class DateViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setDateHeader = mock<SetDateHeader>()
    private val viewModel = DateViewModel(
        setDateHeader = setDateHeader
    )

    @Test
    fun `setDate - Check return failure if have error in SetDateHeader`() =
        runTest {
            whenever(
                setDateHeader(Date(1756928843000))
            ).thenReturn(
                resultFailure(
                    context = "SetDateHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setDate(Date(1756928843000))
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "DateViewModel.setDate -> SetDateHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `setDate - Check return true if SetDateHeader execute successfully`() =
        runTest {
            whenever(
                setDateHeader(Date(1756928843000))
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setDate(Date(1756928843000))
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}