package br.com.usinasantafe.ppc.presenter.view.header.headerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.header.ListHeader
import br.com.usinasantafe.ppc.domain.usecases.header.SetHeaderOpen
import br.com.usinasantafe.ppc.presenter.model.HeaderScreenModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class HeaderListState(
    val headerList: List<HeaderScreenModel> = emptyList(),
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class HeaderListViewModel @Inject constructor(
    private val listHeader: ListHeader,
    private val setHeaderOpen: SetHeaderOpen
) : ViewModel() {

    private val _uiState = MutableStateFlow(HeaderListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun recoverList() = viewModelScope.launch {
        val result = listHeader()
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure,
                )
            }
            return@launch
        }
        val headerList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                headerList = headerList,
            )
        }
    }

    fun setOpen(id: Int) = viewModelScope.launch {
        val result = setHeaderOpen(id)
        if(result.isFailure){
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
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
                flagAccess = result.getOrNull()!!
            )
        }
    }

}
