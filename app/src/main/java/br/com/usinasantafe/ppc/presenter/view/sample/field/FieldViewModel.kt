package br.com.usinasantafe.ppc.presenter.view.sample.field

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.sample.SetFieldSample
import br.com.usinasantafe.ppc.presenter.theme.addTextFieldComma
import br.com.usinasantafe.ppc.presenter.theme.clearTextFieldComma
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.TypeButton
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class FieldState(
    val value: String = "0,000",
    val field: Field = Field.TARE,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class FieldViewModel @Inject constructor(
    private val setFieldSample: SetFieldSample
) : ViewModel() {

    private val _uiState = MutableStateFlow(FieldState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when(typeButton){
            TypeButton.NUMERIC -> {
                val value = addTextFieldComma(uiState.value.value, text)
                _uiState.update {
                    it.copy(value = value)
                }
            }
            TypeButton.CLEAN -> {
                val value = clearTextFieldComma(uiState.value.value)
                _uiState.update {
                    it.copy(value = value)
                }
            }
            TypeButton.OK -> {
                setValue()
            }
            TypeButton.UPDATE -> {}
        }
    }

    private fun clearValue(
        field: Field
    ) {
        _uiState.update {
            it.copy(
                field = field,
                value = "0,000"
            )
        }
    }

    private fun next() = viewModelScope.launch {
        when(uiState.value.field){
            Field.TARE -> clearValue(Field.STALK)
            Field.STALK -> clearValue(Field.WHOLE_CANE)
            Field.WHOLE_CANE -> clearValue(Field.STUMP)
            Field.STUMP -> clearValue(Field.PIECE)
            Field.PIECE -> clearValue(Field.TIP)
            Field.TIP -> clearValue(Field.SLIVERS)
            Field.SLIVERS -> {
                _uiState.update {
                    it.copy(
                        flagDialog = true
                    )
                }
            }
        }
    }

    fun previous() = viewModelScope.launch {
        when(uiState.value.field){
            Field.TARE -> {}
            Field.STALK -> clearValue(Field.TARE)
            Field.WHOLE_CANE -> clearValue(Field.STALK)
            Field.STUMP -> clearValue(Field.WHOLE_CANE)
            Field.PIECE -> clearValue(Field.STUMP)
            Field.TIP -> clearValue(Field.PIECE)
            Field.SLIVERS -> clearValue(Field.TIP)
        }
    }

    private fun setValue() = viewModelScope.launch {
        val result = setFieldSample(
            field = uiState.value.field,
            value = uiState.value.value
        )
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure
                )
            }
            return@launch
        }
        next()
    }

}