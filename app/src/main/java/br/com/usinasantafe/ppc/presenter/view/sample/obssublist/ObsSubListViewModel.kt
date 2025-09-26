package br.com.usinasantafe.ppc.presenter.view.sample.obssublist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.sample.SetSubObsSample
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class ObsSubListState(
    val guineaGrass: Boolean = false,
    val castorOilPlant: Boolean = false,
    val signalGrass: Boolean = false,
    val mucuna: Boolean = false,
    val silkGrass: Boolean = false,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class ObsSubListViewModel @Inject constructor(
    private val setSubObsSample: SetSubObsSample
) : ViewModel() {

    private val _uiState = MutableStateFlow(ObsSubListState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    fun onCheckedGuineaGrass() {
        _uiState.update {
            it.copy(guineaGrass = !uiState.value.guineaGrass)
        }
    }

    fun onCheckedCastorOilPlant() {
        _uiState.update {
            it.copy(castorOilPlant = !uiState.value.castorOilPlant)
        }
    }

    fun onCheckedSignalGrass() {
        _uiState.update {
            it.copy(signalGrass = !uiState.value.signalGrass)
        }
    }

    fun onCheckedMucuna() {
        _uiState.update {
            it.copy(mucuna = !uiState.value.mucuna)
        }
    }

    fun onCheckedSilkGrass() {
        _uiState.update {
            it.copy(silkGrass = !uiState.value.silkGrass)
        }
    }

    fun setSubObs() = viewModelScope.launch {
        val resultSet = setSubObsSample(
            guineaGrass = uiState.value.guineaGrass,
            castorOilPlant = uiState.value.castorOilPlant,
            signalGrass = uiState.value.signalGrass,
            mucuna = uiState.value.mucuna,
            silkGrass = uiState.value.silkGrass
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