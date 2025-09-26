package br.com.usinasantafe.ppc.presenter.view.sample.obssublist

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.sample.SetSubObsSample
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class ObsSubListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setSubObsSample = mock<SetSubObsSample>()
    private val viewModel = ObsSubListViewModel(
        setSubObsSample = setSubObsSample
    )

    @Test
    fun `setSubObs - Check return failure if have error in SetSubObsSample`() =
        runTest {
            whenever(
                setSubObsSample(
                    guineaGrass = true,
                    castorOilPlant = false,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = false
                )
            ).thenReturn(
                resultFailure(
                    context = "SetSubObsSample",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onCheckedGuineaGrass()
            viewModel.onCheckedSignalGrass()
            viewModel.onCheckedMucuna()
            viewModel.setSubObs()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "ObsSubListViewModel.setSubObs -> SetSubObsSample -> java.lang.Exception"
            )
        }

    @Test
    fun `setSubObs - Check return true if SerSubObsList execute successfully`() =
        runTest {
            whenever(
                setSubObsSample(
                    guineaGrass = true,
                    castorOilPlant = false,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = false
                )
            ).thenReturn(
                Result.success(true)
            )
            viewModel.onCheckedGuineaGrass()
            viewModel.onCheckedSignalGrass()
            viewModel.onCheckedMucuna()
            viewModel.setSubObs()
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
        }

}