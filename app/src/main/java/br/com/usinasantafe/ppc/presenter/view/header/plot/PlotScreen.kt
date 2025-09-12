package br.com.usinasantafe.ppc.presenter.view.header.plot

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
fun PlotScreen(
    viewModel: PlotViewModel = hiltViewModel(),
    onNavSection: () -> Unit,
    onNavFront: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PlotContent(
                nroPlot = uiState.nroPlot,
                setTextField = viewModel::setTextField,
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
                onNavSection = onNavSection,
                onNavFront = onNavFront,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun PlotContent(
    nroPlot: String,
    setTextField: (String, TypeButton) -> Unit,
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
    onNavSection: () -> Unit,
    onNavFront: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_plot
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
            value = nroPlot,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
        )
        BackHandler {
            onNavSection()
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
                        id = R.string.text_title_plot
                    )
                )
                Errors.UPDATE -> stringResource(
                    id = R.string.text_update_failure,
                    failure
                )
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_data_invalid,
                    stringResource(
                        id = R.string.text_title_plot
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
            onNavFront()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
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
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreviewUpdate() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = true,
                currentProgress = 0.333f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_plot",
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreviewUpdateSuccess() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "",
                flagFailure = false,
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                currentProgress = 1f,
                levelUpdate = LevelUpdate.SAVE,
                tableUpdate = "tb_plot",
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreviewFieldEmpty() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
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
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreviewFieldInvalid() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
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
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotPagePreviewFieldFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            PlotContent(
                nroPlot = "2",
                setTextField = { _, _ -> },
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
                onNavSection = {},
                onNavFront = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}