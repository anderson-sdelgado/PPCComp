package br.com.usinasantafe.ppc.presenter.view.header.front

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.header.SetFrontHeader
import br.com.usinasantafe.ppc.presenter.theme.addTextField
import br.com.usinasantafe.ppc.presenter.theme.clearTextField
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.TypeButton
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class FrontState(
    val nroFront: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
)

@HiltViewModel
class FrontViewModel @Inject constructor(
    private val setFrontHeader: SetFrontHeader
) : ViewModel() {

    private val _uiState = MutableStateFlow(FrontState())
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
                val nroFront = addTextField(uiState.value.nroFront, text)
                _uiState.update {
                    it.copy(nroFront = nroFront)
                }
            }
            TypeButton.CLEAN -> {
                val nroFront = clearTextField(uiState.value.nroFront)
                _uiState.update {
                    it.copy(nroFront = nroFront)
                }
            }
            TypeButton.OK -> {
                if(uiState.value.nroFront.isEmpty()){
                    val failure =  "FrontViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            failure = failure,
                            errors = Errors.FIELD_EMPTY,
                            flagAccess = false
                        )
                    }
                    return
                }
                setNroFront()
            }
            TypeButton.UPDATE -> {}
        }
    }

    private fun setNroFront() = viewModelScope.launch {
        val resultSet = setFrontHeader(nroFront = uiState.value.nroFront)
        if(resultSet.isFailure){
            val error = resultSet.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                    errors = Errors.EXCEPTION,
                    flagAccess = false
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagAccess = true,
            )
        }
    }

}