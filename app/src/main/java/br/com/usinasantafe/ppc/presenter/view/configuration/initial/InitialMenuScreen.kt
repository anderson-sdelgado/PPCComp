package br.com.usinasantafe.ppc.presenter.view.configuration.initial

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.BuildConfig
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.ItemListDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import br.com.usinasantafe.ppc.utils.StatusSend

@Composable
fun InitialMenuScreen(
    viewModel: InitialMenuViewModel = hiltViewModel(),
    onNavPassword: () -> Unit,
    onNavHeaderList: () -> Unit
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            InitialMenuContent(
                statusSend = uiState.statusSend,
                onCheckAccess = viewModel::onCheckAccess,
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                flagFailure = uiState.flagFailure,
                onNavPassword = onNavPassword,
                onNavHeaderList = onNavHeaderList,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun InitialMenuContent(
    statusSend: StatusSend,
    onCheckAccess: () -> Unit,
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    flagFailure: Boolean,
    onNavPassword: () -> Unit,
    onNavHeaderList: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = (LocalContext.current as? Activity)
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        TitleDesign(
            text = stringResource(
                id = R.string.text_title_initial_menu,
                BuildConfig.VERSION_NAME
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
                        id = R.string.text_item_note
                    ),
                    setActionItem = onCheckAccess,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_config
                    ),
                    setActionItem = onNavPassword,
                    font = 26
                )
            }
            item {
                ItemListDesign(
                    text = stringResource(
                        id = R.string.text_item_out
                    ),
                    setActionItem = {
                        activity?.finish()
                    },
                    font = 26
                )
            }
        }
        Text(
            textAlign = TextAlign.Left,
            text =
                if(failure.isEmpty()) {
                    when (statusSend) {
                        StatusSend.STARTED -> stringResource(id = R.string.text_status_started)
                        StatusSend.SEND -> stringResource(id = R.string.text_status_send)
                        StatusSend.SENT -> stringResource(id = R.string.text_status_sent)
                    }
                } else {
                    "Failure: $failure"
                }
            ,
            fontSize = 22.sp,
            color =
                if(failure.isEmpty()) {
                    when (statusSend) {
                        StatusSend.STARTED -> Color.Red
                        StatusSend.SEND -> Color.Red
                        StatusSend.SENT -> Color.Green
                    }
                } else {
                    Color.Red
                }
            , modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
        )
        BackHandler {}
    }

    if (flagDialog) {
        val text =
            if (!flagFailure) {
                stringResource(id = R.string.text_blocked_access_app)
            } else {
                stringResource(
                    id = R.string.text_failure,
                    failure
                )
            }
        AlertDialogSimpleDesign(
            text = text,
            setCloseDialog = setCloseDialog,
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavHeaderList()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun InitialMenuPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            InitialMenuContent(
                statusSend = StatusSend.STARTED,
                onCheckAccess = {},
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                flagFailure = false,
                onNavPassword = {},
                onNavHeaderList = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}