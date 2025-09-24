package br.com.usinasantafe.ppc.presenter.view.sample.obslist

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.sample.SetObsSample
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ObsListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setObsSample = mock<SetObsSample>()
    private val viewModel = ObsListViewModel(
        setObsSample = setObsSample
    )

    @Test
    fun `setObs - Check return failure if have error in SetObsSample`() =
        runTest {
            whenever(
                setObsSample(
                    stone = true,
                    treeStump = false,
                    weed = true,
                    anthill = false
                )
            ).thenReturn(
                resultFailure(
                    context = "SetObsSample",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onCheckedWeed()
            viewModel.onCheckedStone()
            viewModel.setObs()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ObsListViewModel.setObs -> SetObsSample -> java.lang.Exception"
            )
        }

    @Test
    fun `setObs - Check return true if SetObsSample execute successfully`() =
        runTest {
            whenever(
                setObsSample(
                    stone = true,
                    treeStump = false,
                    weed = true,
                    anthill = false
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onCheckedWeed()
            viewModel.onCheckedStone()
            viewModel.setObs()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}