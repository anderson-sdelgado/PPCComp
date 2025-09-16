package br.com.usinasantafe.ppc.presenter.view.header.front

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
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ButtonsGenericNumeric
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.utils.Errors
import br.com.usinasantafe.ppc.utils.TypeButton

@Composable
fun FrontScreen(
    viewModel: FrontViewModel = hiltViewModel(),
    onNavPlot: () -> Unit,
    onNavHarvester: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            FrontContent(
                nroFront = uiState.nroFront,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavPlot = onNavPlot,
                onNavHarvester = onNavHarvester,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun FrontContent(
    nroFront: String,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavPlot: () -> Unit,
    onNavHarvester: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_front
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
            value = nroFront,
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
            onNavPlot()
        }
    }

    if(flagDialog) {
        val text = when(errors){
            Errors.FIELD_EMPTY -> stringResource(
                id = R.string.text_field_empty,
                stringResource(id = R.string.text_title_front)
            )
            else -> stringResource(
                id = R.string.text_failure,
                failure
            )
        }
        AlertDialogSimpleDesign(
            text = text,
            setCloseDialog = setCloseDialog
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavHarvester()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FrontPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FrontContent(
                nroFront = "5",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavPlot = {},
                onNavHarvester = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrontPagePreviewFieldEmpty() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FrontContent(
                nroFront = "5",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavPlot = {},
                onNavHarvester = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrontPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FrontContent(
                nroFront = "5",
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                errors = Errors.EXCEPTION,
                onNavPlot = {},
                onNavHarvester = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}