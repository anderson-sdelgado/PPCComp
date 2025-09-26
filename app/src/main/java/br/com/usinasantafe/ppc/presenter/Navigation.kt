package br.com.usinasantafe.ppc.presenter

import androidx.navigation.NavHostController
import br.com.usinasantafe.ppc.presenter.Args.CHECK_OPEN_SAMPLE_ARGS
import br.com.usinasantafe.ppc.presenter.Screens.AUDITOR_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.CONFIG_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.DATE_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.FIELD_SAMPLE_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.FRONT_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.HARVESTER_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.HEADER_LIST_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.INITIAL_MENU_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.OBS_LIST_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.OBS_SUB_LIST_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.OPERATOR_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.OS_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.PASSWORD_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.PLOT_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.SAMPLE_LIST_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.SECTION_HEADER_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.SPLASH_SCREEN
import br.com.usinasantafe.ppc.presenter.Screens.TURN_HEADER_SCREEN

object Screens {
    const val SPLASH_SCREEN = "splash"
    const val INITIAL_MENU_SCREEN = "initialMenuScreen"
    const val PASSWORD_SCREEN = "passwordScreen"
    const val CONFIG_SCREEN = "configScreen"
    const val HEADER_LIST_SCREEN = "headerListScreen"
    const val AUDITOR_HEADER_SCREEN = "auditorHeaderScreen"
    const val DATE_HEADER_SCREEN = "dateHeaderScreen"
    const val TURN_HEADER_SCREEN = "turnHeaderScreen"
    const val OS_HEADER_SCREEN = "osHeaderScreen"
    const val SECTION_HEADER_SCREEN = "sectionHeaderScreen"
    const val PLOT_HEADER_SCREEN = "plotHeaderScreen"
    const val FRONT_HEADER_SCREEN = "frontHeaderScreen"
    const val HARVESTER_HEADER_SCREEN = "harvesterHeaderScreen"
    const val OPERATOR_HEADER_SCREEN = "operatorHeaderScreen"
    const val SAMPLE_LIST_SCREEN = "sampleListScreen"
    const val FIELD_SAMPLE_SCREEN = "fieldSampleScreen"
    const val OBS_LIST_SCREEN = "obsListScreen"
    const val OBS_SUB_LIST_SCREEN = "obsSubListScreen"
}

object Args {
    const val CHECK_OPEN_SAMPLE_ARGS = "checkOpenSample"
}

object Routes {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val INITIAL_MENU_ROUTE = INITIAL_MENU_SCREEN
    const val PASSWORD_ROUTE = PASSWORD_SCREEN
    const val CONFIG_ROUTE = CONFIG_SCREEN
    const val HEADER_LIST_ROUTE = HEADER_LIST_SCREEN
    const val AUDITOR_HEADER_ROUTE = AUDITOR_HEADER_SCREEN
    const val DATE_HEADER_ROUTE = DATE_HEADER_SCREEN
    const val TURN_HEADER_ROUTE = TURN_HEADER_SCREEN
    const val OS_HEADER_ROUTE = OS_HEADER_SCREEN
    const val SECTION_HEADER_ROUTE = SECTION_HEADER_SCREEN
    const val PLOT_HEADER_ROUTE = PLOT_HEADER_SCREEN
    const val FRONT_HEADER_ROUTE = FRONT_HEADER_SCREEN
    const val HARVESTER_HEADER_ROUTE = HARVESTER_HEADER_SCREEN
    const val OPERATOR_HEADER_ROUTE = OPERATOR_HEADER_SCREEN
    const val SAMPLE_LIST_ROUTE = SAMPLE_LIST_SCREEN
    const val FIELD_SAMPLE_ROUTE = "$FIELD_SAMPLE_SCREEN/{$CHECK_OPEN_SAMPLE_ARGS}"
    const val OBS_LIST_ROUTE = OBS_LIST_SCREEN
    const val OBS_SUB_LIST_ROUTE = OBS_SUB_LIST_SCREEN
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

    fun navigateToAuditorHeader() {
        navController.navigate(AUDITOR_HEADER_SCREEN)
    }

    fun navigateToDateHeader() {
        navController.navigate(DATE_HEADER_SCREEN)
    }

    fun navigateToTurnHeader() {
        navController.navigate(TURN_HEADER_SCREEN)
    }

    fun navigateToOSHeader() {
        navController.navigate(OS_HEADER_SCREEN)
    }

    fun navigateToSectionHeader() {
        navController.navigate(SECTION_HEADER_SCREEN)
    }

    fun navigateToPlotHeader() {
        navController.navigate(PLOT_HEADER_SCREEN)
    }

    fun navigateToFrontHeader() {
        navController.navigate(FRONT_HEADER_SCREEN)
    }

    fun navigateToHarvesterHeader() {
        navController.navigate(HARVESTER_HEADER_SCREEN)
    }

    fun navigateToOperatorHeader() {
        navController.navigate(OPERATOR_HEADER_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

    ////////////////////////// Sample //////////////////////////////////

    fun navigateToSampleList() {
        navController.navigate(SAMPLE_LIST_SCREEN)
    }

    fun navigateToFieldSample(
        checkOpenSample: Boolean = true
    ){
        navController.navigate("${FIELD_SAMPLE_SCREEN}/${checkOpenSample}")
    }

    fun navigateToObsList() {
        navController.navigate(OBS_LIST_SCREEN)
    }

    fun navigateToObsSubList() {
        navController.navigate(OBS_SUB_LIST_SCREEN)
    }

    ////////////////////////////////////////////////////////////////////

}