package br.com.usinasantafe.ppc.presenter.view.sample.obslist

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.presenter.theme.TextButtonDesign

@Composable
fun ObsListScreen(
    viewModel: ObsListViewModel = hiltViewModel(),
    onNavField: () -> Unit,
    onNavSampleList: () -> Unit,
    onNavObsSubList: () -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObsListContent(
                stone = uiState.stone,
                onCheckedStone = viewModel::onCheckedStone,
                treeStump = uiState.treeStump,
                onCheckedTreeStump = viewModel::onCheckedTreeStump,
                weed = uiState.weed,
                onCheckedWeed = viewModel::onCheckedWeed,
                anthill = uiState.anthill,
                onCheckedAnthill = viewModel::onCheckedAnthill,
                setObs = viewModel::setObs,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavField = onNavField,
                onNavSampleList = onNavSampleList,
                onNavObsSubList = onNavObsSubList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ObsListContent(
    stone: Boolean,
    onCheckedStone: () -> Unit,
    treeStump: Boolean,
    onCheckedTreeStump: () -> Unit,
    weed: Boolean,
    onCheckedWeed: () -> Unit,
    anthill: Boolean,
    onCheckedAnthill: () -> Unit,
    setObs: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onNavField: () -> Unit,
    onNavSampleList: () -> Unit,
    onNavObsSubList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_obs,
            )
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedStone() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = stone,
                        onCheckedChange = { onCheckedStone() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_stone,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedTreeStump() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = treeStump,
                        onCheckedChange = { onCheckedTreeStump() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_treeStump,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedAnthill() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = anthill,
                        onCheckedChange = { onCheckedAnthill() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_anthill,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedWeed() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = weed,
                        onCheckedChange = { onCheckedWeed() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_weed,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
        )  {
            Button(
                onClick = onNavField,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_return)
                )
            }
            Button(
                onClick = setObs,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_ok)
                )
            }
        }
        BackHandler {
        }
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
            if(weed) onNavObsSubList() else onNavSampleList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ObsListPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObsListContent(
                stone = false,
                onCheckedStone = {},
                treeStump = true,
                onCheckedTreeStump = {},
                weed = false,
                onCheckedWeed = {},
                anthill = false,
                onCheckedAnthill = {},
                setObs = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onNavField = {},
                onNavSampleList = {},
                onNavObsSubList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ObsListPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObsListContent(
                stone = false,
                onCheckedStone = {},
                treeStump = true,
                onCheckedTreeStump = {},
                weed = false,
                onCheckedWeed = {},
                anthill = false,
                onCheckedAnthill = {},
                setObs = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onNavField = {},
                onNavSampleList = {},
                onNavObsSubList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}