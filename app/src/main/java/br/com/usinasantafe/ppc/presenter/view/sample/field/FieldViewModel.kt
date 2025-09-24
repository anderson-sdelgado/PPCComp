package br.com.usinasantafe.ppc.presenter.view.sample.field

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.sample.CheckWeightRelationTare
import br.com.usinasantafe.ppc.domain.usecases.sample.SetWeightSample
import br.com.usinasantafe.ppc.presenter.Args.CHECK_OPEN_SAMPLE_ARGS
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
    val weight: String = "0,000",
    val field: Field = Field.TARE,
    val flagDialogCheck: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class FieldViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val checkWeightRelationTare: CheckWeightRelationTare,
    private val setWeightSample: SetWeightSample
) : ViewModel() {

    private val check: Boolean = saveStateHandle[CHECK_OPEN_SAMPLE_ARGS]!!

    private val _uiState = MutableStateFlow(FieldState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    init {
        val field = if(check) Field.TARE else Field.SLIVERS
        _uiState.update {
            it.copy(
                field = field
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) = viewModelScope.launch {
        when(typeButton){
            TypeButton.NUMERIC -> {
                val value = addTextFieldComma(uiState.value.weight, text)
                _uiState.update {
                    it.copy(weight = value)
                }
            }
            TypeButton.CLEAN -> {
                val value = clearTextFieldComma(uiState.value.weight)
                _uiState.update {
                    it.copy(weight = value)
                }
            }
            TypeButton.OK -> {
                if(uiState.value.weight == "0,000"){
                    _uiState.update {
                        it.copy(
                            flagDialogCheck = true
                        )
                    }
                    return@launch
                }
                checkWeight()
            }
            TypeButton.UPDATE -> {}
        }
    }

    fun onDialogCheck(
        flagDialogCheck: Boolean,
    ) {
        _uiState.update {
            it.copy(
                flagDialogCheck = flagDialogCheck
            )
        }
    }

    fun clearValue(
        field: Field
    ) {
        _uiState.update {
            it.copy(
                field = field,
                weight = "0,000",
                flagDialogCheck = false
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
                        flagAccess = true
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

    fun checkWeight() = viewModelScope.launch {
        if(uiState.value.field != Field.TARE){
            val resultCheck = checkWeightRelationTare(uiState.value.weight)
            if(resultCheck.isFailure){
                val error = resultCheck.exceptionOrNull()!!
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
            val check = resultCheck.getOrNull()!!
            if(!check){
                val failure =  "FieldViewModel.setTextField.OK -> Weight Invalid!"
                Timber.e(failure)
                _uiState.update {
                    it.copy(
                        flagDialog = true,
                        errors = Errors.INVALID,
                        failure = failure
                    )
                }
                return@launch
            }
        }
        setWeight()
    }

    fun setWeight() = viewModelScope.launch {
        val resultSet = setWeightSample(
            field = uiState.value.field,
            value = uiState.value.weight
        )
        if(resultSet.isFailure){
            val error = resultSet.exceptionOrNull()!!
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