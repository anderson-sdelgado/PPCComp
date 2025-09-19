package br.com.usinasantafe.ppc.presenter.view.sample.field

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
import br.com.usinasantafe.ppc.utils.Field
import br.com.usinasantafe.ppc.utils.TypeButton

@Composable
fun FieldScreen(
    viewModel: FieldViewModel = hiltViewModel(),
    onNavSampleList: () -> Unit,
    onNavObsList: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            FieldContent(
                value = uiState.value,
                field = uiState.field,
                previous = viewModel::previous,
                setTextField = viewModel::setTextField,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                errors = uiState.errors,
                onNavSampleList = onNavSampleList,
                onNavObsList = onNavObsList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun FieldContent(
    value: String,
    field: Field,
    previous: () -> Unit,
    setTextField: (String, TypeButton) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    errors: Errors,
    onNavSampleList: () -> Unit,
    onNavObsList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = when(field) {
        Field.TARE -> stringResource(
            id = R.string.text_title_tare
        )
        Field.STUMP -> stringResource(
            id = R.string.text_title_stump
        )
        Field.PIECE -> stringResource(
            id = R.string.text_title_piece
        )
        Field.TIP -> stringResource(
            id = R.string.text_title_tip
        )
        Field.SLIVERS -> stringResource(
            id = R.string.text_title_slivers
        )
        Field.STALK -> stringResource(
            id = R.string.text_title_stalk
        )
        Field.WHOLE_CANE -> stringResource(
            id = R.string.text_title_whole_cane
        )
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = title
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
            value = value,
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
            if(field == Field.TARE) onNavSampleList() else previous()
        }
    }

    if(flagDialog) {
        val text = when(errors){
            Errors.FIELD_EMPTY -> stringResource(
                id = R.string.text_field_empty,
                title
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
            onNavObsList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FieldPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            FieldContent(
                value = "0,000",
                field = Field.TARE,
                previous = {},
                setTextField = { _, _ -> },
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                errors = Errors.FIELD_EMPTY,
                onNavSampleList = {},
                onNavObsList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}