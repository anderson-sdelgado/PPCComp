package br.com.usinasantafe.ppc.presenter.view.header.turn

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
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
import br.com.usinasantafe.ppc.presenter.theme.ItemListDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.presenter.theme.TextButtonDesign

@Composable
fun TurnScreen(
    viewModel: TurnViewModel = hiltViewModel(),
    onNavDate: () -> Unit,
    onNavOS: () -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TurnContent(
                setTurn = viewModel::setTurn,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavDate = onNavDate,
                onNavOS = onNavOS,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun TurnContent(
    setTurn: (Int) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavDate: () -> Unit,
    onNavOS: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_turn
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_turn_1
                    ),
                    setActionItem = { setTurn(1) },
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_turn_2
                    ),
                    setActionItem = { setTurn(2) },
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_turn_3
                    ),
                    setActionItem = { setTurn(3) },
                    font = 26
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavDate,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
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
            onNavOS()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TurnPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnContent(
                setTurn = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavDate = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TurnPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TurnContent(
                setTurn = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavDate = {},
                onNavOS = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}