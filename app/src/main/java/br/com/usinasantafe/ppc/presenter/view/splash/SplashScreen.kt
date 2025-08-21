package br.com.usinasantafe.ppc.presenter.view.splash

import android.window.SplashScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.presenter.theme.AlertDialogSimpleDesign
import br.com.usinasantafe.ppc.presenter.theme.TitleDesign
import br.com.usinasantafe.ppc.presenter.theme.PPCTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onNavInitialMenu: () -> Unit,
) {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                viewModel.startApp()
            }

            SplashContent(
                setCloseDialog = viewModel::setCloseDialog,
                flagAccess = uiState.flagAccess,
                flagDialog = uiState.flagDialog,
                failure = uiState.failure,
                onNavInitialMenu = onNavInitialMenu,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun SplashContent(
    setCloseDialog: () -> Unit,
    flagAccess: Boolean,
    flagDialog: Boolean,
    failure: String,
    onNavInitialMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visibility by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                visibility = !visibility
                delay(2000)
            }
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 1100)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1100))
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_name),
                contentScale = ContentScale.Fit,
                modifier = modifier.size(250.dp)
            )
        }
    }

    if(flagDialog) {
        AlertDialogSimpleDesign(
            text = stringResource(id = R.string.text_failure, failure),
            setCloseDialog = setCloseDialog,
            setActionButtonOK = onNavInitialMenu
        )
    }

    LaunchedEffect(flagAccess) {
        if(flagAccess) {
            onNavInitialMenu()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreview() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = false,
                failure = "",
                onNavInitialMenu = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPagePreviewFailure() {
    PPCTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SplashContent(
                setCloseDialog = {},
                flagAccess = false,
                flagDialog = true,
                failure = "Failure",
                onNavInitialMenu = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}