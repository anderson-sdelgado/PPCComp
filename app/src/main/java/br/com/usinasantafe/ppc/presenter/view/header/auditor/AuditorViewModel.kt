package br.com.usinasantafe.ppc.presenter.view.header.auditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.flow.CheckColab
import br.com.usinasantafe.ppc.domain.usecases.flow.SetAuditor
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableColab
import br.com.usinasantafe.ppc.presenter.Args.POS_AUDITOR_ARGS
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

data class AuditorState(
    val posAuditor: Int = 0,
    val regAuditor: String = "",
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

fun ResultUpdateModel.resultUpdateToAuditor(classAndMethod: String): AuditorState {
    val fail = if(failure.isNotEmpty()){
        val ret = "$classAndMethod -> ${this.failure}"
        Timber.e(ret)
        ret
    } else {
        this.failure
    }
    return AuditorState(
        flagDialog = this.flagDialog,
        failure = fail,
        flagFailure = this.flagFailure,
        errors = this.errors,
        flagProgress = this.flagProgress,
        currentProgress = this.currentProgress,
        levelUpdate = this.levelUpdate,
        tableUpdate = this.tableUpdate,
    )
}

@HiltViewModel
class AuditorViewModel @Inject constructor(
    saveStateHandle: SavedStateHandle,
    private val updateTableColab: UpdateTableColab,
    private val setAuditor: SetAuditor,
    private val checkColab: CheckColab
) : ViewModel() {

    private val posAuditor: Int = saveStateHandle[POS_AUDITOR_ARGS]!!

    private val _uiState = MutableStateFlow(AuditorState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(flagDialog = false)
        }
    }

    init {
        _uiState.update {
            it.copy(
                posAuditor = posAuditor
            )
        }
    }

    fun setTextField(
        text: String,
        typeButton: TypeButton
    ) {
        when (typeButton) {
            TypeButton.NUMERIC -> {
                val regAuditor = addTextField(uiState.value.regAuditor, text)
                _uiState.update {
                    it.copy(regAuditor = regAuditor)
                }
            }

            TypeButton.CLEAN -> {
                val regAuditor = clearTextField(uiState.value.regAuditor)
                _uiState.update {
                    it.copy(regAuditor = regAuditor)
                }
            }

            TypeButton.OK -> {
                if (
                    uiState.value.regAuditor.isEmpty() &&
                    posAuditor == 1
                ) {
                    val failure =  "AuditorViewModel.setTextField.OK -> Field Empty!"
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
                setRegAuditor()
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

    private fun setRegAuditor() = viewModelScope.launch {
        if(uiState.value.regAuditor.isEmpty()) {
            next()
            return@launch
        }
        val resultCheck = checkColab(uiState.value.regAuditor)
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
            val failure =  "AuditorViewModel.setRegAuditor -> RegAuditor Invalid!"
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
        val resultAuditor = setAuditor(
            pos = uiState.value.posAuditor,
            regAuditor = uiState.value.regAuditor
        )
        if(resultAuditor.isFailure){
            val error = resultAuditor.exceptionOrNull()!!
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
        next()
    }

    fun next() = viewModelScope.launch {
        if (
            uiState.value.regAuditor.isEmpty() &&
            posAuditor == 2
        ) {
            _uiState.update {
                it.copy(
                    flagAccess = true,
                )
            }
            return@launch
        }
        if(uiState.value.posAuditor < 3){
            _uiState.update {
                it.copy(
                    posAuditor = uiState.value.posAuditor + 1,
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    flagAccess = true,
                )
            }
        }
    }

    fun ret() = viewModelScope.launch {
        _uiState.update {
            it.copy(
                posAuditor = uiState.value.posAuditor - 1,
            )
        }
    }

    fun updateAllDatabase(): Flow<AuditorState> = flow {
        val sizeAllUpdate = sizeUpdate(1f)
        var auditorState = AuditorState()
        updateTableColab(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            auditorState = it.resultUpdateToAuditor(getClassAndMethod())
            emit(
                it.resultUpdateToAuditor(getClassAndMethod())
            )
        }
        if (auditorState.flagFailure) return@flow
        emit(
            AuditorState(
                flagDialog = true,
                flagProgress = false,
                flagFailure = false,
                levelUpdate = LevelUpdate.FINISH_UPDATE_COMPLETED,
                currentProgress = 1f,
            )
        )
    }
}