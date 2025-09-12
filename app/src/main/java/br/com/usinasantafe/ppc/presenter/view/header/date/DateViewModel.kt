package br.com.usinasantafe.ppc.presenter.view.header.date

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.header.SetDateHeader
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

data class DateState(
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class DateViewModel @Inject constructor(
    private val setDateHeader: SetDateHeader
) : ViewModel() {

    private val _uiState = MutableStateFlow(DateState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun setDate(date: Date) = viewModelScope.launch {
        val result = setDateHeader(date)
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
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