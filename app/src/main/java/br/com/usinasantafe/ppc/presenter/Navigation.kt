package br.com.usinasantafe.ppc.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.ppc.presenter.Args.POS_AUDITOR_ARGS
import br.com.usinasantafe.ppc.presenter.Screens.AUDITOR_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.HEADER_LIST_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.SPLASH_SCREEN

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val HEADER_LIST_SCREEN = "headerListScreen"
    const val AUDITOR_HEADER_SCREEN = "auditorHeaderScreen"
}

object Args {
    const val POS_AUDITOR_ARGS = "posAuditor"
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val HEADER_LIST_ROUTE = HEADER_LIST_SCREEN
    const val AUDITOR_HEADER_ROUTE = "$AUDITOR_HEADER_SCREEN/{$POS_AUDITOR_ARGS}"
}

class NavigationActions(private val navController: NavHostController) {

    ///////////////////////// Splash //////////////////////////////////

    fun navigateToSplash() {
        navController.navigate(SPLASH_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ///////////////////////// Config //////////////////////////////////

    fun navigateToInitialMenu() {
        navController.navigate(INITIAL_MENU_SCREEN)
    }

    fun navigateToPassword() {
        navController.navigate(PASSWORD_SCREEN)
    }

    fun navigateToConfig() {
        navController.navigate(CONFIG_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Header //////////////////////////////////

    fun navigateToHeaderList() {
        navController.navigate(HEADER_LIST_SCREEN)
    }

    fun navigateToAuditorHeader(
        position: Int = 1
    ) {
        navController.navigate("${AUDITOR_HEADER_SCREEN}/${position}")
    }


    ////////////////////////////////////////////////////////////////////

}