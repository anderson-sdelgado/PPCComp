package br.com.usinasantafe.ppc.presenter.view.header.os

import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.flow.CheckNroOS
import br.com.usinasantafe.ppc.domain.usecases.flow.SetNroOSHeader
import br.com.usinasantafe.ppc.presenter.model.ResultCheckDataWebServiceModel
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.StatusCon
import br.com.usinasantafe.ppc.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

@ExperimentalCoroutinesApi
class OSViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val checkNroOS = mock<CheckNroOS>()
    private val setNroOSHeader = mock<SetNroOSHeader>()
    private val viewModel = OSViewModel(
        checkNroOS = checkNroOS,
        setNroOSHeader = setNroOSHeader
    )

    @Test
    fun `setTextField - Check add char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroOS,
            "1"
        )
    }

    @Test
    fun `setTextField - Check remover char`() {
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "2",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "3",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "4",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "5",
            TypeButton.NUMERIC
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "APAGAR",
            TypeButton.CLEAN
        )
        viewModel.setTextField(
            "1",
            TypeButton.NUMERIC
        )
        assertEquals(
            viewModel.uiState.value.nroOS,
            "121"
        )
    }

    @Test
    fun `checkAndSet - Check return failure if have error in CheckNroOS`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                resultFailure(
                    context = "ICheckNroOS",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.checkAndSet -> ICheckNroOS -> java.lang.Exception"
            )
        }

    @Test
    fun `checkAndSet - Check return false if nroOS is inexistent`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.OK,
                        check = false
                    )
                )
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                false
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `checkAndSet - Check return failure if have error in SetNroOSHeader`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.OK,
                        check = true
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.checkAndSet -> ISetNroOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `checkAndSet - Check return true if nroOS is existent and SetNroOSHeader execute successfully`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.OK,
                        check = true
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `checkAndSet - Check return failure if have error SocketTimeoutException in CheckNroOS and have error in SetNroOSHeader`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.SLOW
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.checkAndSet -> ISetNroOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `checkAndSet - Check return true  if have error SocketTimeoutException in CheckNroOS and SetNroOSHeader execute successfully`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.SLOW
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

    @Test
    fun `checkAndSet - Check return failure if not have connection internet in CheckNroOS and have error in SetNroOSHeader`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.WITHOUT
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                resultFailure(
                    context = "ISetNroOSHeader",
                    message = "-",
                    cause = Exception()
                )
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "OSViewModel.checkAndSet -> ISetNroOSHeader -> java.lang.Exception"
            )
        }

    @Test
    fun `checkAndSet - Check return true  if not have connection internet in CheckNroOS and SetNroOSHeader execute successfully`() =
        runTest {
            whenever(
                checkNroOS(nroOS = "123456")
            ).thenReturn(
                Result.success(
                    ResultCheckDataWebServiceModel(
                        statusCon = StatusCon.WITHOUT
                    )
                )
            )
            whenever(
                setNroOSHeader(nroOS = "123456")
            ).thenReturn(
                Result.success(true)
            )
            viewModel.setTextField(
                "123456",
                TypeButton.NUMERIC
            )
            viewModel.setTextField(
                "OK",
                TypeButton.OK
            )
            assertEquals(
                viewModel.uiState.value.flagDialog,
                false
            )
            assertEquals(
                viewModel.uiState.value.flagAccess,
                true
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
            assertEquals(
                viewModel.uiState.value.flagProgress,
                false
            )
        }

}