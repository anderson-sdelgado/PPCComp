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
    onNavHeaderList: () -> Unit,
    onNavTare: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.recoverList()
            }

            SampleListContent(
                sampleList = uiState.sampleList,
                typeState = uiState.typeState,
                indexSelection = uiState.indexSelection,
                flagDialogCheck = uiState.flagDialogCheck,
                onDialogCheck = viewModel::onDialogCheck,
                onTypeState = viewModel::onTypeState,
                onSelection = viewModel::onSelection,
                finish = viewModel::finish,
                delete = viewModel::delete,
                deleteItem = viewModel::deleteItem,
                flagReturn = uiState.flagReturn,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavHeaderList = onNavHeaderList,
                onNavTare = onNavTare,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SampleListContent(
    sampleList: List<SampleScreenModel>,
    typeState: TypeStateSampleList,
    indexSelection: Int,
    flagDialogCheck: Boolean,
    onDialogCheck: (Boolean) -> Unit,
    onTypeState: (TypeStateSampleList) -> Unit,
    onSelection: (Int, Int) -> Unit,
    finish: () -> Unit,
    delete: () -> Unit,
    deleteItem: () -> Unit,
    flagReturn: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavHeaderList: () -> Unit,
    onNavTare: () -> Unit,
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
        if(sampleList.isEmpty()) {
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
                        setActionItem = {
                            onSelection(sample.id, index + 1)
                            onTypeState(TypeStateSampleList.DELETE_ITEM)
                            onDialogCheck(true)
                        },
                        font = 24,
                        padding = 6
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Button(
            onClick = onNavTare,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextButtonDesign(
                text = stringResource(id = R.string.text_pattern_insert)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    onTypeState(TypeStateSampleList.DELETE_ALL)
                    onDialogCheck(true)
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_delete)
                )
            }
            Button(
                onClick = {
                    onTypeState(TypeStateSampleList.FINISH)
                    onDialogCheck(true)
                },
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_finish)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onNavHeaderList,
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
                TypeStateSampleList.FINISH -> stringResource(id = R.string.text_msg_finish_header)
                TypeStateSampleList.DELETE_ALL -> stringResource(id = R.string.text_msg_delete_header)
                TypeStateSampleList.DELETE_ITEM -> stringResource(
                    id = R.string.text_msg_delete_sample,
                    indexSelection
                )
            },
            setCloseDialog = {
                onDialogCheck(false)
            },
            setActionButtonYes = {
                when(typeState){
                    TypeStateSampleList.FINISH -> finish()
                    TypeStateSampleList.DELETE_ALL -> delete()
                    TypeStateSampleList.DELETE_ITEM -> deleteItem()
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

    LaunchedEffect(flagReturn) {
        if(flagReturn) {
            onNavHeaderList()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleListPagePreviewEmptyList() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SampleListContent(
                sampleList = emptyList(),
                typeState = TypeStateSampleList.FINISH,
                indexSelection = 0,
                flagDialogCheck = false,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                finish = {},
                delete = {},
                deleteItem = {},
                flagReturn = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavHeaderList = {},
                onNavTare = {},
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
                typeState = TypeStateSampleList.FINISH,
                indexSelection = 0,
                flagDialogCheck = false,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                finish = {},
                delete = {},
                deleteItem = {},
                setCloseDialog = {},
                flagReturn = false,
                flagDialog = false,
                failure = "",
                onNavHeaderList = {},
                onNavTare = {},
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
                indexSelection = 0,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                flagDialogCheck = false,
                typeState = TypeStateSampleList.FINISH,
                finish = {},
                delete = {},
                deleteItem = {},
                flagReturn = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavHeaderList = {},
                onNavTare = {},
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
                indexSelection = 0,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                flagDialogCheck = true,
                typeState = TypeStateSampleList.FINISH,
                finish = {},
                delete = {},
                deleteItem = {},
                flagReturn = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "Failure",
                onNavHeaderList = {},
                onNavTare = {},
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
                indexSelection = 0,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                flagDialogCheck = true,
                typeState = TypeStateSampleList.DELETE_ALL,
                finish = {},
                delete = {},
                deleteItem = {},
                flagReturn = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "Failure",
                onNavHeaderList = {},
                onNavTare = {},
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
                indexSelection = 1,
                onDialogCheck = {},
                onTypeState = {},
                onSelection = { _, _ -> },
                flagDialogCheck = true,
                typeState = TypeStateSampleList.DELETE_ITEM,
                finish = {},
                delete = {},
                deleteItem = {},
                flagReturn = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "Failure",
                onNavHeaderList = {},
                onNavTare = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}