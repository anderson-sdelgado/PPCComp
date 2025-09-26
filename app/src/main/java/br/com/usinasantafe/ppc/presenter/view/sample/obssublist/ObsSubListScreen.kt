package br.com.usinasantafe.ppc.presenter.view.sample.obssublist

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
fun ObsSubListScreen(
    viewModel: ObsSubListViewModel = hiltViewModel(),
    onObsList: () -> Unit,
    onNavSampleList: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ObsSubListContent(
                guineaGrass = uiState.guineaGrass,
                onCheckedGuineaGrass = viewModel::onCheckedGuineaGrass,
                castorOilPlant = uiState.castorOilPlant,
                onCheckedCastorOilPlant = viewModel::onCheckedCastorOilPlant,
                signalGrass = uiState.signalGrass,
                onCheckedSignalGrass = viewModel::onCheckedSignalGrass,
                mucuna = uiState.mucuna,
                onCheckedMucuna = viewModel::onCheckedMucuna,
                silkGrass = uiState.silkGrass,
                onCheckedSilkGrass = viewModel::onCheckedSilkGrass,
                setSubObs = viewModel::setSubObs,
                flagAccess = uiState.flagAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onObsList = onObsList,
                onNavSampleList = onNavSampleList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun ObsSubListContent(
    guineaGrass: Boolean,
    onCheckedGuineaGrass: () -> Unit,
    castorOilPlant: Boolean,
    onCheckedCastorOilPlant: () -> Unit,
    signalGrass: Boolean,
    onCheckedSignalGrass: () -> Unit,
    mucuna: Boolean,
    onCheckedMucuna: () -> Unit,
    silkGrass: Boolean,
    onCheckedSilkGrass: () -> Unit,
    setSubObs: () -> Unit,
    flagAccess: Boolean,
    setCloseDialog: () -> Unit,
    flagDialog: Boolean,
    failure: String,
    onObsList: () -> Unit,
    onNavSampleList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_item_weed,
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
                        .clickable { onCheckedGuineaGrass() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = guineaGrass,
                        onCheckedChange = { onCheckedGuineaGrass() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_guinea_grass,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedCastorOilPlant() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = castorOilPlant,
                        onCheckedChange = { onCheckedCastorOilPlant() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_castor_oil_plant,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedSignalGrass() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = signalGrass,
                        onCheckedChange = { onCheckedSignalGrass() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_signal_grass,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedMucuna() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = mucuna,
                        onCheckedChange = { onCheckedMucuna() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_mucuna,
                        ),
                        fontSize = 22.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCheckedSilkGrass() },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = silkGrass,
                        onCheckedChange = { onCheckedSilkGrass() },
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_item_silk_grass,
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
                onClick = onObsList,
                modifier = Modifier.weight(1f)
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_return),
                    padding = 10
                )
            }
            Button(
                onClick = setSubObs,
                modifier = Modifier.weight(1f),
            ) {
                TextButtonDesign(
                    text = stringResource(id = R.string.text_pattern_ok),
                    padding = 10
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
            onNavSampleList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ObsSubListPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObsSubListContent(
                guineaGrass = true,
                onCheckedGuineaGrass = {},
                castorOilPlant = false,
                onCheckedCastorOilPlant = {},
                signalGrass = false,
                onCheckedSignalGrass = {},
                mucuna = true,
                onCheckedMucuna = {},
                silkGrass = false,
                onCheckedSilkGrass = {},
                setSubObs = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = false,
                failure = "",
                onObsList = {},
                onNavSampleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ObsSubListPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            ObsSubListContent(
                guineaGrass = true,
                onCheckedGuineaGrass = {},
                castorOilPlant = false,
                onCheckedCastorOilPlant = {},
                signalGrass = false,
                onCheckedSignalGrass = {},
                mucuna = true,
                onCheckedMucuna = {},
                silkGrass = false,
                onCheckedSilkGrass = {},
                setSubObs = {},
                flagAccess = false,
                setCloseDialog = {},
                flagDialog = true,
                failure = "Failure",
                onObsList = {},
                onNavSampleList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}