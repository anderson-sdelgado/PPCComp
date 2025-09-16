package br.com.usinasantafe.ppc.presenter.view.sample.samplelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.analysis.CloseAnalysis
import br.com.usinasantafe.ppc.domain.usecases.analysis.DeleteAnalysis
import br.com.usinasantafe.ppc.domain.usecases.sample.DeleteSample
import br.com.usinasantafe.ppc.domain.usecases.sample.ListSample
import br.com.usinasantafe.ppc.presenter.Args.ID_HEADER_ARGS
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
    val typeState: TypeStateSampleList = TypeStateSampleList.CLOSE,
    val idHeader: Int = 0,
    val idSelection: Int = 0,
    val indexSelection: Int = 0,
    val flagDialogCheck: Boolean = false,
    val flagAccess: Boolean? = null,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SampleListViewModel @Inject constructor(
    private val listSample: ListSample,
    private val closeAnalysis: CloseAnalysis,
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

    fun setDialogCheck(flagDialogCheck: Boolean) {
        _uiState.update {
            it.copy(flagDialogCheck = flagDialogCheck)
        }
    }

    suspend fun recoverList() {
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
            return
        }
        val list = result.getOrNull()!!
        _uiState.update {
            it.copy(
                sampleList = list
            )
        }
    }

    fun close() = viewModelScope.launch {
        val result = closeAnalysis()
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
                flagAccess = false
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
                flagAccess = false
            )
        }
    }

    fun deleteItem(idSample: Int) = viewModelScope.launch {
        val result = deleteSample(
            idSample = idSample
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
        recoverList()
    }


}