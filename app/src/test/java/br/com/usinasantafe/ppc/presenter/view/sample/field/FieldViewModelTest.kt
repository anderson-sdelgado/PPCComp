package br.com.usinasantafe.ppc.presenter.view.sample.field

import androidx.lifecycle.SavedStateHandle
import br.com.usinasantafe.ppc.MainCoroutineRule
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.usecases.sample.CheckWeightRelationTare
import br.com.usinasantafe.ppc.domain.usecases.sample.SetWeightSample
import br.com.usinasantafe.ppc.presenter.Args.CHECK_OPEN_SAMPLE_ARGS
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.TypeButton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class FieldViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val setWeightSample = mock<SetWeightSample>()
    private val checkWeightRelationTare = mock<CheckWeightRelationTare>()
    private fun createViewModel(
        check: Boolean = true
    ) = FieldViewModel(
        SavedStateHandle(
            mapOf(
                CHECK_OPEN_SAMPLE_ARGS to check
            )
        ),
        setWeightSample = setWeightSample,
        checkWeightRelationTare = checkWeightRelationTare
    )

    @Test
    fun `setTextField - Check add char`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("4", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("8", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.weight,
                "14,508"
            )
        }

    @Test
    fun `setTextField - Check remover char`()  =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("3", TypeButton.NUMERIC)
            viewModel.setTextField("4", TypeButton.NUMERIC)
            viewModel.setTextField("5", TypeButton.NUMERIC)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("APAGAR", TypeButton.CLEAN)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("9", TypeButton.NUMERIC)
            assertEquals(
                viewModel.uiState.value.weight,
                "1,219"
            )
        }

    @Test
    fun `setTextField - Check return failure if weight is zero`() =
        runTest {
            val viewModel = createViewModel()
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialogCheck,
                true
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in CheckWeightRelationTare and field is different of TARE`() =
        runTest {
            whenever(
                checkWeightRelationTare(
                    value = "1,020"
                )
            ).thenReturn(
                resultFailure(
                    context = "CheckWeightRelationTare",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.clearValue(Field.STALK)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "FieldViewModel.checkWeight -> CheckWeightRelationTare -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setTextField - Check return failure if weight digit is not valid and field is different of TARE`() =
        runTest {
            whenever(
                checkWeightRelationTare(
                    value = "1,020"
                )
            ).thenReturn(
                Result.success(false)
            )
            val viewModel = createViewModel()
            viewModel.clearValue(Field.STALK)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "FieldViewModel.setTextField.OK -> Weight Invalid!"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.INVALID
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in SetFieldSample and field is different of TARE`() =
        runTest {
            whenever(
                checkWeightRelationTare(
                    value = "1,020"
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setWeightSample(
                    field = Field.STALK,
                    value = "1,020"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetWeightSample",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.clearValue(Field.STALK)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "FieldViewModel.setWeight -> SetWeightSample -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setTextField - Check return true if SetFieldSample execute successfully and field is different of TARE`() =
        runTest {
            whenever(
                checkWeightRelationTare(
                    value = "1,020"
                )
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                setWeightSample(
                    field = Field.STALK,
                    value = "1,020"
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.clearValue(Field.STALK)
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.field,
                Field.WHOLE_CANE
            )
            assertEquals(
                viewModel.uiState.value.weight,
                "0,000"
            )
        }

    @Test
    fun `setTextField - Check return failure if have error in SetFieldSample and field is TARE`() =
        runTest {
            whenever(
                setWeightSample(
                    field = Field.TARE,
                    value = "1,020"
                )
            ).thenReturn(
                resultFailure(
                    context = "SetWeightSample",
                    message = "-",
                    cause = Exception()
                )
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.flagDialog,
                true
            )
            assertEquals(
                viewModel.uiState.value.failure,
                "FieldViewModel.setWeight -> SetWeightSample -> java.lang.Exception"
            )
            assertEquals(
                viewModel.uiState.value.errors,
                Errors.EXCEPTION
            )
        }

    @Test
    fun `setTextField - Check return true if SetFieldSample execute successfully and field is TARE`() =
        runTest {
            whenever(
                setWeightSample(
                    field = Field.TARE,
                    value = "1,020"
                )
            ).thenReturn(
                Result.success(true)
            )
            val viewModel = createViewModel()
            viewModel.setTextField("1", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("2", TypeButton.NUMERIC)
            viewModel.setTextField("0", TypeButton.NUMERIC)
            viewModel.setTextField("OK", TypeButton.OK)
            assertEquals(
                viewModel.uiState.value.field,
                Field.STALK
            )
            assertEquals(
                viewModel.uiState.value.weight,
                "0,000"
            )
        }

    @Test
    fun `init - Check alter field if check is false`() =
        runTest {
            val viewModel = createViewModel(false)
            assertEquals(
                viewModel.uiState.value.field,
                Field.SLIVERS
            )
        }

    @Test
    fun `init - Check return field if check is false`() =
        runTest {
            val viewModel = createViewModel(false)
            assertEquals(
                viewModel.uiState.value.field,
                Field.SLIVERS
            )
            viewModel.previous()
            assertEquals(
                viewModel.uiState.value.field,
                Field.TIP
            )
        }


}