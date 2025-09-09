package br.com.usinasantafe.ppc.presenter.view.header.section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.flow.CheckSection
import br.com.usinasantafe.ppc.domain.usecases.flow.SetSectionHeader
import br.com.usinasantafe.ppc.domain.usecases.update.UpdateTableSection
import br.com.usinasantafe.ppc.presenter.model.ResultUpdateModel
import br.com.usinasantafe.ppc.presenter.theme.addTextField
import br.com.usinasantafe.ppc.presenter.theme.clearTextField
import br.com.usinasantafe.ppc.presenter.view.header.auditor.AuditorState
import br.com.usinasantafe.ppc.presenter.view.header.auditor.resultUpdateToAuditor
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

data class SectionState(
    val nroSection: String = "",
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

fun ResultUpdateModel.resultUpdateToSection(
    classAndMethod: String,
    currentState: SectionState
): SectionState {
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
class SectionViewModel @Inject constructor(
    private val updateTableSection: UpdateTableSection,
    private val checkSection: CheckSection,
    private val setSectionHeader: SetSectionHeader,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SectionState())
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
                val nroSection = addTextField(uiState.value.nroSection, text)
                _uiState.update {
                    it.copy(nroSection = nroSection)
                }
            }
            TypeButton.CLEAN -> {
                val nroSection = clearTextField(uiState.value.nroSection)
                _uiState.update {
                    it.copy(nroSection = nroSection)
                }
            }
            TypeButton.OK -> {
                if(uiState.value.nroSection.isEmpty()){
                    val failure =  "SectionViewModel.setTextField.OK -> Field Empty!"
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
                setNroSection()
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

    private fun setNroSection() = viewModelScope.launch {
        val resultCheck = checkSection(uiState.value.nroSection)
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
            val failure =  "SectionViewModel.setNroSection -> NroSection Invalid!"
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
        val resultSet = setSectionHeader(uiState.value.nroSection)
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

    fun updateAllDatabase(): Flow<SectionState> = flow {
        val sizeAllUpdate = sizeUpdate(1f)
        var lastEmittedState: SectionState? = null
        updateTableSection(
            sizeAll = sizeAllUpdate,
            count = 1f
        ).collect {
            val currentGlobalState = _uiState.value
            val newState = it.resultUpdateToSection(
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

