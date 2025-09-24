package br.com.usinasantafe.ppc.presenter.view.sample.obslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.sample.SetObsSample
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ObsListState(
    val stone: Boolean = false,
    val treeStump: Boolean = false,
    val weed: Boolean = false,
    val anthill: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class ObsListViewModel @Inject constructor(
    private val setObsSample: SetObsSample
) : ViewModel() {

    private val _uiState = MutableStateFlow(ObsListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onCheckedStone() {
        _uiState.update {
            it.copy(stone = !uiState.value.stone)
        }
    }

    fun onCheckedTreeStump() {
        _uiState.update {
            it.copy(treeStump = !uiState.value.treeStump)
        }
    }

    fun onCheckedAnthill() {
        _uiState.update {
            it.copy(anthill = !uiState.value.anthill)
        }
    }

    fun onCheckedWeed() {
        _uiState.update {
            it.copy(weed = !uiState.value.weed)
        }
    }


    fun setObs() = viewModelScope.launch {
        val resultSet = setObsSample(
            stone = uiState.value.stone,
            treeStump = uiState.value.treeStump,
            weed = uiState.value.weed,
            anthill = uiState.value.anthill
        )
        if(resultSet.isFailure){
            val error = resultSet.exceptionOrNull()!!
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
                flagAccess = true
            )
        }
    }

}