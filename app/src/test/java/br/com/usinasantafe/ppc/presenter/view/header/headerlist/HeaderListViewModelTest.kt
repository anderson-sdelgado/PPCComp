package br.com.usinasantafe.ppc.presenter.view.header.headerlist

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.header.ListHeader
import br.com.usinasantafe.ppc.presenter.model.HeaderScreenModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class HeaderListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val listHeader = mock<ListHeader>()
    private val viewModel = HeaderListViewModel(
        listHeader = listHeader
    )

    @Test
    fun `recoverList - Check return failure if have error in ListHeader`() =
        runTest {
            whenever(
                listHeader()
            ).thenReturn(
                resultFailure(
                    context = "ListHeader",
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
                "HeaderListViewModel.recoverList -> ListHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `recoverList - Check return emptyList if ListHeader return emptyList`() =
        runTest {
            whenever(
                listHeader()
            ).thenReturn(
                Result.success(
                    emptyList()
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.headerList,
                emptyList<HeaderScreenModel>()
            )
        }

    @Test
    fun `recoverList - Check return list if ListHeader return list of data`() =
        runTest {
            whenever(
                listHeader()
            ).thenReturn(
                Result.success(
                    listOf(
                        HeaderScreenModel(
                            id = 1,
                            harvester = 1,
                            operator = 1,
                            front = 1,
                            turn = 1,
                            qtdSample = 1
                        )
                    )
                )
            )
            viewModel.recoverList()
            assertEquals(
                viewModel.uiState.value.headerList,
                listOf(
                    HeaderScreenModel(
                        id = 1,
                        harvester = 1,
                        operator = 1,
                        front = 1,
                        turn = 1,
                        qtdSample = 1
                    )
                )
            )
        }

}