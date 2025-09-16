package br.com.usinasantafe.ppc.presenter.view.sample.samplelist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogCheckDesign
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ItemListSampleDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.presenter.theme.TextButtonDesign
import br.com.usinasantafe.ppc.utils.TypeStateSampleList

@Composable
fun SampleListScreen(
    viewModel: SampleListViewModel = hiltViewModel(),
    onHeaderList: () -> Unit,
    onSampleList: (Int) -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverList()
            }

            SampleListContent(
                sampleList = emptyList(),
                typeState = uiState.typeState,
                idSelection = uiState.idSelection,
                indexSelection = uiState.indexSelection,
                flagDialogCheck = uiState.flagDialogCheck,
                setDialogCheck = viewModel::setDialogCheck,
                close = viewModel::close,
                delete = viewModel::delete,
                deleteItem = viewModel::deleteItem,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SampleListContent(
    sampleList: List<SampleScreenModel>,
    typeState: TypeStateSampleList,
    idSelection: Int,
    indexSelection: Int,
    flagDialogCheck: Boolean,
    setDialogCheck: (Boolean) -> Unit,
    close: () -> Unit,
    delete: () -> Unit,
    deleteItem: (Int) -> Unit,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_sample_list
            )
        )
        if(sampleList.isEmpty()){
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        id = R.string.text_msg_no_data_sample
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
                itemsIndexed(sampleList) { index, sample ->
                    ItemListSampleDesign(
                        id = sample.id,
                        pos = index + 1,
                        stalk = sample.stalk,
                        wholeCane = sample.wholeCane,
                        stump = sample.stump,
                        piece = sample.piece,
                        tip = sample.tip,
                        slivers = sample.slivers,
                        obs = sample.obs,
                        setActionItem = {  },
                        font = 24,
                        padding = 6
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_insert)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_delete)
                )
            }
            Button(
                onClick = {},
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_close)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_return)
            )
        }
        BackHandler {}
    }

    if (flagDialogCheck) {
        AlertDialogCheckDesign(
            text = when (typeState) {
                TypeStateSampleList.CLOSE -> stringResource(id = R.string.text_msg_close_header)
                TypeStateSampleList.DELETE_ALL -> stringResource(id = R.string.text_msg_delete_header)
                TypeStateSampleList.DELETE_ITEM -> stringResource(
                    id = R.string.text_msg_delete_sample,
                    indexSelection
                )
            },
            setCloseDialog = { setDialogCheck(false) },
            setActionButtonYes = {
                when(typeState){
                    TypeStateSampleList.CLOSE -> TODO()
                    TypeStateSampleList.DELETE_ALL -> TODO()
                    TypeStateSampleList.DELETE_ITEM -> TODO()
                }
            }
        )
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

}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewEmptyList() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList = emptyList(),
                typeState = TypeStateSampleList.CLOSE,
                idSelection = 0,
                indexSelection = 0,
                flagDialogCheck = false,
                setDialogCheck = {},
                close = {},
                delete = {},
                deleteItem = {},
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewList() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList =
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        ),
                        SampleScreenModel(
                            id = 2,
                            stalk = 2.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        )
                    ),
                typeState = TypeStateSampleList.CLOSE,
                idSelection = 0,
                indexSelection = 0,
                flagDialogCheck = false,
                setDialogCheck = {},
                close = {},
                delete = {},
                deleteItem = {},
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewListFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList =
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        ),
                        SampleScreenModel(
                            id = 2,
                            stalk = 2.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        )
                    ),
                idSelection = 0,
                indexSelection = 0,
                setCloseDialog = {},
                setDialogCheck = {},
                flagDialogCheck = false,
                typeState = TypeStateSampleList.CLOSE,
                close = {},
                delete = {},
                deleteItem = {},
                flagDialog = true,
                failure = "Failure",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewListClose() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList =
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        ),
                        SampleScreenModel(
                            id = 2,
                            stalk = 2.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        )
                    ),
                idSelection = 0,
                indexSelection = 0,
                setCloseDialog = {},
                setDialogCheck = {},
                flagDialogCheck = true,
                typeState = TypeStateSampleList.CLOSE,
                close = {},
                delete = {},
                deleteItem = {},
                flagDialog = false,
                failure = "Failure",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewListDeleteAll() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList =
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        ),
                        SampleScreenModel(
                            id = 2,
                            stalk = 2.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        )
                    ),
                idSelection = 0,
                indexSelection = 0,
                setCloseDialog = {},
                setDialogCheck = {},
                flagDialogCheck = true,
                typeState = TypeStateSampleList.DELETE_ALL,
                close = {},
                delete = {},
                deleteItem = {},
                flagDialog = false,
                failure = "Failure",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewListDeleteSample() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList =
                    listOf(
                        SampleScreenModel(
                            id = 1,
                            stalk = 1.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        ),
                        SampleScreenModel(
                            id = 2,
                            stalk = 2.005,
                            wholeCane = 2.003,
                            stump = 3.250,
                            piece = 4.356,
                            tip = 5.540,
                            slivers = 6.263,
                            obs = "Teste"
                        )
                    ),
                idSelection = 1,
                indexSelection = 1,
                setCloseDialog = {},
                setDialogCheck = {},
                flagDialogCheck = true,
                typeState = TypeStateSampleList.DELETE_ITEM,
                close = {},
                delete = {},
                deleteItem = {},
                flagDialog = false,
                failure = "Failure",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}