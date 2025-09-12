package br.com.usinasantafe.ppc.presenter.view.header.os

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.header.CheckOS
import br.com.usinasantafe.ppc.domain.usecases.header.SetOSHeader
import br.com.usinasantafe.ppc.presenter.theme.addTextField
import br.com.usinasantafe.ppc.presenter.theme.clearTextField
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.StatusCon
import br.com.usinasantafe.ppc.utils.TypeButton
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OSState(
    val nroOS: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val msgProgress: String = "",
)

@HiltViewModel
class OSViewModel @Inject constructor(
    private val checkOS: CheckOS,
    private val setOSHeader: SetOSHeader
) : ViewModel() {

    private val _uiState = MutableStateFlow(OSState())
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
                val nroOS = addTextField(uiState.value.nroOS, text)
                _uiState.update {
                    it.copy(nroOS = nroOS)
                }
            }
            TypeButton.CLEAN -> {
                val nroOS = clearTextField(uiState.value.nroOS)
                _uiState.update {
                    it.copy(nroOS = nroOS)
                }
            }
            TypeButton.OK -> {
                if (uiState.value.nroOS.isEmpty()) {
                    val failure = "OSViewModel.setTextField.OK -> Field Empty!"
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
                checkAndSet()
            }
            TypeButton.UPDATE -> {}
        }
    }

    private fun checkAndSet() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                flagProgress = true,
            )
        }
        val resultCheck = checkOS(uiState.value.nroOS)
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagProgress = false,
                )
            }
            return@launch
        }
        val ret = resultCheck.getOrNull()!!
        var check = true
        when(ret.statusCon){
            StatusCon.WITHOUT -> {
                val failure = "OSViewModel.setTextField.OK -> Without Connection"
                Timber.e(failure)
            }
            StatusCon.SLOW -> {
                val failure = "OSViewModel.setTextField.OK -> Slow Connection"
                Timber.e(failure)
            }
            StatusCon.OK -> {
                check = ret.check!!
            }
        }
        if(!check){
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.INVALID,
                    flagProgress = false,
                    flagAccess = false
                )
            }
            return@launch
        }
        val resultSet = setOSHeader(uiState.value.nroOS)
        if (resultSet.isFailure) {
            val error = resultSet.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagProgress = false,
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagDialog = false,
                flagAccess = true,
                errors = Errors.INVALID,
                flagProgress = false,
            )
        }
    }

}