package br.com.usinasantafe.ppc.presenter.view.sample.samplelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.analysis.FinishAnalysis
import br.com.usinasantafe.ppc.domain.usecases.analysis.DeleteAnalysis
import br.com.usinasantafe.ppc.domain.usecases.sample.DeleteSample
import br.com.usinasantafe.ppc.domain.usecases.sample.ListSample
import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import br.com.usinasantafe.ppc.utils.TypeStateSampleList
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SampleListState(
    val sampleList: List<SampleScreenModel> = emptyList(),
    val typeState: TypeStateSampleList = TypeStateSampleList.FINISH,
    val idSelection: Int = 0,
    val indexSelection: Int = 0,
    val flagDialogCheck: Boolean = false,
    val flagReturn: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SampleListViewModel @Inject constructor(
    private val listSample: ListSample,
    private val finishAnalysis: FinishAnalysis,
    private val deleteAnalysis: DeleteAnalysis,
    private val deleteSample: DeleteSample,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SampleListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onTypeState(typeState: TypeStateSampleList) {
        _uiState.update {
            it.copy(typeState = typeState)
        }
    }

    fun onSelection(idSelection: Int, indexSelection: Int) {
        _uiState.update {
            it.copy(
                idSelection = idSelection,
                indexSelection = indexSelection
            )
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

    fun recoverList() = viewModelScope.launch {
        val result = listSample()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        val sampleList = result.getOrNull()!!
        _uiState.update {
            it.copy(
                sampleList = sampleList
            )
        }
    }

    fun finish() = viewModelScope.launch {
        val result = finishAnalysis()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagReturn = true
            )
        }
    }

    fun delete() = viewModelScope.launch {
        val result = deleteAnalysis()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        _uiState.update {
            it.copy(
                flagReturn = true
            )
        }
    }

    fun deleteItem() = viewModelScope.launch {
        val result = deleteSample(
            id = uiState.value.idSelection
        )
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    failure = failure
                )
            }
            return@launch
        }
        onDialogCheck(false)
        recoverList()
    }


}