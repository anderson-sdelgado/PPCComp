package br.com.usinasantafe.ppc.presenter.view.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.usinasantafe.ppc.domain.usecases.background.StartWorkManager
import br.com.usinasantafe.ppc.domain.usecases.config.CheckUpdateApp
import br.com.usinasantafe.ppc.domain.usecases.config.UpdateApp
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class SplashState(
    val flagUpdate: Boolean = false,
    val currentProgress: Float = 0f,
    val flagAccess: Boolean = false,
    val flagDialog: Boolean = false,
    val failure: String = "",
)

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUpdateApp: CheckUpdateApp,
    private val updateApp: UpdateApp,
    private val startWorkManager: StartWorkManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashState())
    val uiState = _uiState.asStateFlow()

    fun setCloseDialog() {
        _uiState.update {
            it.copy(
                flagDialog = false,
                flagAccess = true
            )
        }
    }

    fun startApp(version: String) = viewModelScope.launch {
        val resultCheck = checkUpdateApp(version)
        if(resultCheck.isFailure){
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            _uiState.update {
                it.copy(
                    flagDialog = true,
                    flagAccess = false,
                    failure = failure
                )
            }
            return@launch
        }
        val check = resultCheck.getOrNull()!!
        _uiState.update {
            it.copy(
                flagUpdate = check
            )
        }
        if(check) {
            updateApp()
                .collect { result ->
                    if (result.isFailure) {
                        val error = result.exceptionOrNull()!!
                        val failure = "${getClassAndMethod()} -> ${error.message} -> ${error.cause}"
                        _uiState.update {
                            it.copy(
                                flagDialog = true,
                                flagAccess = false,
                                flagUpdate = false,
                                failure = failure
                            )
                        }
                    } else {
                        val currentProgress = result.getOrNull()!!
                        _uiState.update {
                            it.copy(
                                flagUpdate = true,
                                currentProgress = currentProgress
                            )
                        }
                    }
                }
        } else {
            startWorkManager()
        }
        _uiState.update {
            it.copy(
                flagAccess = true
            )
        }
    }

}