package br.com.usinasantafe.ppc.presenter.view.sample.samplelist

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.analysis.FinishAnalysis
import br.com.usinasantafe.ppc.domain.usecases.analysis.DeleteAnalysis
import br.com.usinasantafe.ppc.domain.usecases.sample.DeleteSample
import br.com.usinasantafe.ppc.domain.usecases.sample.ListSample
import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class SampleListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listSample = mock<ListSample>()
    private val finishAnalysis = mock<FinishAnalysis>()
    private val deleteAnalysis = mock<DeleteAnalysis>()
    private val deleteSample = mock<DeleteSample>()
    private val viewModel = SampleListViewModel(
        listSample = listSample,
        finishAnalysis = finishAnalysis,
        deleteAnalysis = deleteAnalysis,
        deleteSample = deleteSample
    )

    @Test
    fun `recoverList - Check return failure if have error in ListSample`() =
        runTest {
            whenever(
                listSample()
            ).thenReturn(
                resultFailure(
                    context = "ListSample",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SampleListViewModel.recoverList -> ListSample -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return true if ListSample execute successfully`() =
        runTest {
            whenever(
                listSample()
            ).thenReturn(
                Result.success(
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.0,
                            wholeCane = 1.0,
                            stump = 1.0,
                            piece = 1.0,
                            tip = 1.0,
                            slivers = 1.0,
                            obs = ""
                        )
                    )
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.sampleList,
                listOf(
                    SampleScreenModel(
                        id = 1,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        obs = ""
                    )
                )
            )
        }

    @Test
    fun `close - Check return failure if have error in CloseAnalysis`() =
        runTest {
            whenever(
                finishAnalysis()
            ).thenReturn(
                resultFailure(
                    context = "CloseAnalysis",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.finish()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SampleListViewModel.close -> CloseAnalysis -> java.lang.Exception"
            )
        }

    @Test
    fun `close - Check return true if CloseAnalysis execute successfully`() =
        runTest {
            whenever(
                finishAnalysis()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.finish()
            assertEquals(
                viewModel.uiState.value.flagReturn,
                false
            )
        }

    @Test
    fun `delete - Check return failure if have error in DeleteAnalysis`() =
        runTest {
            whenever(
                deleteAnalysis()
            ).thenReturn(
                resultFailure(
                    context = "DeleteAnalysis",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.delete()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SampleListViewModel.delete -> DeleteAnalysis -> java.lang.Exception"
            )
        }

    @Test
    fun `delete - Check return true if DeleteAnalysis execute successfully`() =
        runTest {
            whenever(
                deleteAnalysis()
            ).thenReturn(
                Result.success(true)
            )
            viewModel.delete()
            assertEquals(
                viewModel.uiState.value.flagReturn,
                false
            )
        }

    @Test
    fun `deleteItem - Check return failure if have error in DeleteSample`() =
        runTest {
            whenever(
                deleteSample(1)
            ).thenReturn(
                resultFailure(
                    context = "DeleteSample",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.onSelection(1, 1)
            viewModel.deleteItem()
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "SampleListViewModel.deleteItem -> DeleteSample -> java.lang.Exception"
            )
        }

    @Test
    fun `deleteItem - Check return true if DeleteSample execute successfully`() =
        runTest {
            whenever(
                deleteSample(1)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                listSample()
            ).thenReturn(
                Result.success(
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.0,
                            wholeCane = 1.0,
                            stump = 1.0,
                            piece = 1.0,
                            tip = 1.0,
                            slivers = 1.0,
                            obs = ""
                        )
                    )
                )
            )
            viewModel.onSelection(1, 1)
            viewModel.deleteItem()
            assertEquals(
                viewModel.uiState.value.sampleList,
                listOf(
                    SampleScreenModel(
                        id = 1,
                        stalk = 1.0,
                        wholeCane = 1.0,
                        stump = 1.0,
                        piece = 1.0,
                        tip = 1.0,
                        slivers = 1.0,
                        obs = ""
                    )
                )
            )
        }

}