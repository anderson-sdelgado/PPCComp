package br.com.usinasantafe.ppc.presenter.view.header.operator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.header.CheckColab
import br.com.usinasantafe.ppc.domain.usecases.header.SetOperatorHeader
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import br.com.usinasantafe.ppc.presenter.theme.addTextField
import br.com.usinasantafe.ppc.presenter.theme.clearTextField
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.LevelUpdate
import br.com.usinasantafe.ppc.utils.TypeButton
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import br.com.usinasantafe.ppc.utils.sizeUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class OperatorState(
    val regOperator: String = "",
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
    val flagFailure: Boolean = false,
    val errors: Errors = Errors.FIELD_EMPTY,
    val flagProgress: Boolean = false,
    val currentProgress: Float = 0.0f,
    val levelUpdate: LevelUpdate? = null,
    val tableUpdate: String = "",
)

fun ResultUpdateModel.resultUpdateToOperator(
    classAndMethod: String,
    currentState: OperatorState
): OperatorState {
    val checkProgress = failure.isEmpty()
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return currentState.copy(
        flagDialog = this.flagDialog,
        failure = fail,
        flagFailure = this.flagFailure,
        errors = this.errors,
        flagProgress = checkProgress,
        currentProgress = this.currentProgress,
        levelUpdate = this.levelUpdate,
        tableUpdate = this.tableUpdate,
    )
}

@HiltViewModel
class OperatorViewModel @Inject constructor(
    private val checkColab: CheckColab,
    private val setOperatorHeader: SetOperatorHeader,
    private val updateTableColab: UpdateTableColab
) : ViewModel() {

    private val _uiState = MutableStateFlow(OperatorState())
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
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val regOperator = addTextField(uiState.value.regOperator, text)
                _uiState.update {
                    it.copy(regOperator = regOperator)
                }
            }
            TypeButton.CLEAN -> {
                val regOperator = clearTextField(uiState.value.regOperator)
                _uiState.update {
                    it.copy(regOperator = regOperator)
                }
            }
            TypeButton.OK -> {
                if(uiState.value.regOperator.isEmpty()){
                    val failure =  "OperatorViewModel.setTextField.OK -> Field Empty!"
                    Timber.e(failure)
                    _uiState.update {
                        it.copy(
                            flagDialog = true,
                            flagFailure = true,
                            errors = Errors.FIELD_EMPTY,
                            failure = failure,
                            flagAccess = false
                        )
                    }
                    return
                }
                setNroOperator()
            }
            TypeButton.UPDATE -> {
                viewModelScope.launch {
                    updateAllDatabase().collect { stateUpdate ->
                        _uiState.value = stateUpdate
                    }
                }
            }
        }
    }
    private fun setNroOperator() = viewModelScope.launch {
        val resultCheck = checkColab(uiState.value.regOperator)
        if(resultCheck.isFailure){
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
                    flagAccess = false
                )
            }
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        if(!check) {
            val failure =  "OperatorViewModel.setNroOperator -> NroOperator Invalid!"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.INVALID,
                    failure = failure,
                    flagAccess = false
                )
            }
            return@launch
        }
        val resultSet = setOperatorHeader(uiState.value.regOperator)
        if(resultSet.isFailure){
            val error = resultSet.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagFailure = true,
                    errors = Errors.EXCEPTION,
                    failure = failure,
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

    fun updateAllDatabase(): Flow<OperatorState> = flow {
        val sizeAllUpdate = sizeUpdate(1f)
        var lastEmittedState: OperatorState? = null
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToOperator(
                classAndMethod = getClassAndMethod(),
                currentState = currentGlobalState
            )
            lastEmittedState = newState
            emit(newState)
        }
        if (lastEmittedState!!.flagFailure) return@flow
        val finalCurrentState = _uiState.value
        emit(
            finalCurrentState.copy(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }

}