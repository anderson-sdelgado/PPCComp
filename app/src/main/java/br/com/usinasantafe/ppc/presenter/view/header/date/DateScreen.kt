package br.com.usinasantafe.ppc.presenter.view.header.date

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.presenter.theme.TextButtonDesign
import java.util.Date

@Composable
fun DateScreen(
    viewModel: DateViewModel = hiltViewModel(),
    onNavAuditor: () -> Unit,
    onNavTurn: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DateContent(
                setDate = viewModel::setDate,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavAuditor = onNavAuditor,
                onNavTurn = onNavTurn,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(
    setDate: (Date) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavAuditor: () -> Unit,
    onNavTurn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_date
            )
        )
        DatePicker(
            state = datePickerState,
            modifier = Modifier
                .fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onNavAuditor,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_cancel)
                )
            }
            Button(
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    val date = Date(selectedMillis!! + (3 * 60  * 60 * 1000))
//                    val date = Date(selectedMillis!!)
                    setDate(date)
                },
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_ok)
                )
            }
        }
        BackHandler {}
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(
                id = R.string.text_failure,
                failure
            ),
            setCloseDialog = setCloseDialog
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavTurn()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DatePagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DateContent(
                setDate = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavAuditor = {},
                onNavTurn = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatePagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DateContent(
                setDate = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavAuditor = {},
                onNavTurn = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}