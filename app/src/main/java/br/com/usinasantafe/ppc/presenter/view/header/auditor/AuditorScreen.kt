package br.com.usinasantafe.ppc.presenter.view.header.auditor

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogProgressDesign
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.LevelUpdate
import br.com.usinasantafe.ppc.utils.TypeButton

@Composable
fun AuditorScreen(
    viewModel: AuditorViewModel = hiltViewModel(),
    onNavHeaderList: () -> Unit,
    onNavDate: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AuditorContent(
                posAuditor = uiState.posAuditor,
                regAuditor = uiState.regAuditor,
                setTextField = viewModel::setTextField,
                ret = viewModel::ret,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagFailure = uiState.flagFailure,
                errors = uiState.errors,
                flagProgress = uiState.flagProgress,
                currentProgress = uiState.currentProgress,
                levelUpdate = uiState.levelUpdate,
                tableUpdate = uiState.tableUpdate,
                onNavHeaderList = onNavHeaderList,
                onNavDate = onNavDate,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun AuditorContent(
    posAuditor: Int,
    regAuditor: String,
    setTextField: (String, TypeButton) -> Unit,
    ret: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    flagFailure: Boolean,
    errors: Errors,
    flagProgress: Boolean,
    currentProgress: Float,
    levelUpdate: LevelUpdate?,
    tableUpdate: String,
    onNavHeaderList: () -> Unit,
    onNavDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_auditor,
                posAuditor
            )
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrectEnabled = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Previous
            ),
            textStyle = TextStyle(
                textAlign = TextAlign.Right,
                fontSize = 28.sp,
            ),
            readOnly = true,
            value = regAuditor,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField
        )
        BackHandler {
            if(posAuditor == 1){
                onNavHeaderList()
            }else{
                ret()
            }
        }

    }

    if (flagProgress) {
        val msgProgress = when(levelUpdate){
            LevelUpdate.RECOVERY -> stringResource(id = R.string.text_msg_recovery, tableUpdate)
            LevelUpdate.CLEAN -> stringResource(id = R.string.text_msg_clean, tableUpdate)
            LevelUpdate.SAVE -> stringResource(id = R.string.text_msg_save, tableUpdate)
            LevelUpdate.GET_TOKEN -> stringResource(id = R.string.text_msg_get_token)
            LevelUpdate.SAVE_TOKEN -> stringResource(id = R.string.text_msg_save_token)
            LevelUpdate.FINISH_UPDATE_INITIAL -> stringResource(id = R.string.text_msg_finish_update_initial)
            LevelUpdate.FINISH_UPDATE_COMPLETED -> stringResource(id = R.string.text_msg_finish_update_completed)
            null -> stringResource(
                id = R.string.text_update_failure,
                failure
            )
            else -> stringResource(
                id = R.string.text_flow_inexistent,
            )
        }
        AlertDialogProgressDesign(
            currentProgress = currentProgress,
            msgProgress = msgProgress
        )
    }

    if (flagDialog) {
        val text = if (flagFailure) {
            when(errors) {
                Errors.FIELD_EMPTY -> stringResource(
                    id = R.string.text_field_empty,
                    stringResource(
                        id = R.string.text_title_auditor,
                        posAuditor
                    )
                )
                Errors.UPDATE -> stringResource(
                    id = R.string.text_update_failure,
                    failure
                )
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_data_invalid,
                    stringResource(
                        id = R.string.text_title_auditor,
                        posAuditor
                    )
                )
                else -> stringResource(
                    id = R.string.text_failure,
                    failure
                )
            }
        } else stringResource(id = R.string.text_msg_finish_update_completed)
        AlertDialogSimpleDesign(
            text = text,
            setCloseDialog = setCloseDialog,
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavDate()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 1,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                currentProgress = 0f,
                levelUpdate = null,
                tableUpdate = "",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewPos2() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                currentProgress = 0f,
                levelUpdate = null,
                tableUpdate = "",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewUpdate() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = true,
                currentProgress = 0.333f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_colab",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewUpdateSuccess() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_colab",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewFieldEmpty() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "",
                flagFailure = true,
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_colab",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewInvalid() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "",
                flagFailure = true,
                errors = Errors.INVALID,
                flagProgress = false,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_colab",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuditorPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AuditorContent(
                posAuditor = 2,
                regAuditor = "19759",
                setTextField = { _, _ -> },
                ret = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                flagFailure = true,
                errors = Errors.EXCEPTION,
                flagProgress = false,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_colab",
                onNavHeaderList = {},
                onNavDate = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}