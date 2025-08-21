package br.com.usinasantafe.ppc.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.ppc.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.SPLASH_SCREEN

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
}

object Args {
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
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

    ////////////////////////////////////////////////////////////////////

}