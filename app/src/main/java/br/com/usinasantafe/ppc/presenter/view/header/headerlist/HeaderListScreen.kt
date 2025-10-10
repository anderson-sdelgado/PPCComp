package br.com.usinasantafe.ppc.presenter.view.header.headerlist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.model.HeaderScreenModel
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ItemListHeaderDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.presenter.theme.TextButtonDesign

@Composable
fun HeaderListScreen(
    viewModel: HeaderListViewModel = hiltViewModel(),
    onNavAuditor: () -> Unit,
    onNavSampleList: () -> Unit,
    onNavSplash: () -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverList()
            }

            HeaderListContent(
                headerList = uiState.headerList,
                setOpen = viewModel::setOpen,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavAuditor = onNavAuditor,
                onNavSampleList = onNavSampleList,
                onNavSplash = onNavSplash,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HeaderListContent(
    headerList: List<HeaderScreenModel>,
    setOpen: (Int) -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavAuditor: () -> Unit,
    onNavSampleList: () -> Unit,
    onNavSplash: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_header_list
            )
        )
        if(headerList.isEmpty()){
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.text_msg_no_data_header
                    ),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(headerList) { header ->
                    ItemListHeaderDesign(
                        id = header.id,
                        harvester = header.harvester,
                        operator = header.operator,
                        front = header.front,
                        turn = header.turn,
                        sample = header.qtdSample,
                        setActionItem = { setOpen(header.id) },
                        font = 24,
                        padding = 6
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = onNavAuditor,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_insert)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavSplash,
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
            onNavSampleList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HeaderListPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HeaderListContent(
                headerList = emptyList(),
                setOpen = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavAuditor = {},
                onNavSampleList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderListPagePreviewWithData() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HeaderListContent(
                headerList = listOf(
                    HeaderScreenModel(
                        id = 1,
                        harvester = 1,
                        operator = 1,
                        front = 1,
                        turn = 1,
                        qtdSample = 1
                    ),
                    HeaderScreenModel(
                        id = 2,
                        harvester = 2,
                        operator = 2,
                        front = 2,
                        turn = 2,
                        qtdSample = 0
                    )
                ),
                setOpen = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavAuditor = {},
                onNavSampleList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderListPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HeaderListContent(
                headerList = listOf(
                    HeaderScreenModel(
                        id = 1,
                        harvester = 1,
                        operator = 1,
                        front = 1,
                        turn = 1,
                        qtdSample = 1
                    ),
                    HeaderScreenModel(
                        id = 2,
                        harvester = 2,
                        operator = 2,
                        front = 2,
                        turn = 2,
                        qtdSample = 0
                    )
                ),
                setOpen = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavAuditor = {},
                onNavSampleList = {},
                onNavSplash = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}