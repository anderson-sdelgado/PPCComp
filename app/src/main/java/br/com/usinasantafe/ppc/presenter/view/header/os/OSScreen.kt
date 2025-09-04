package br.com.usinasantafe.ppc.presenter.view.header.os

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
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogProgressIndeterminateDesign
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.TypeButton

@Composable
fun OSScreen(
    viewModel: OSViewModel = hiltViewModel(),
    onNavTurn: () -> Unit,
    onNavSection: () -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            OSContent(
                nroOS = uiState.nroOS,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                flagProgress = uiState.flagProgress,
                msgProgress = uiState.msgProgress,
                onNavTurn = onNavTurn,
                onNavSection = onNavSection,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun OSContent(
    nroOS: String,
    setTextField: (String, TypeButton) -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    flagProgress: Boolean,
    msgProgress: String,
    onNavTurn: () -> Unit,
    onNavSection: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_os
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
            value = nroOS,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ButtonsGenericNumeric(
            setActionButton = setTextField,
            flagUpdate = false
        )
        BackHandler {
            onNavTurn()
        }

        if(flagDialog) {
            val text = when (errors) {
                Errors.FIELD_EMPTY -> stringResource(
                    id = R.string.text_field_empty,
                    stringResource(id = R.string.text_title_os)
                )
                Errors.UPDATE,
                Errors.TOKEN,
                Errors.EXCEPTION -> stringResource(
                    id = R.string.text_failure,
                    failure
                )
                Errors.INVALID -> stringResource(
                    id = R.string.text_input_data_non_existent,
                    stringResource(id = R.string.text_title_os)
                )
            }
            AlertDialogSimpleDesign(
                text = text,
                setCloseDialog = setCloseDialog,
            )
        }

        if (flagProgress) {
            AlertDialogProgressIndeterminateDesign(
                msgProgress = msgProgress
            )
        }
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavSection()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun OSPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            OSContent(
                nroOS = "123456",
                setTextField = { _, _ -> },
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                flagProgress = false,
                msgProgress = "",
                onNavTurn = {},
                onNavSection = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}