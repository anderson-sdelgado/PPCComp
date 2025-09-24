package br.com.usinasantafe.ppc.presenter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.usinasantafe.ppc.presenter.Args.CHECK_OPEN_SAMPLE_ARGS
import br.com.usinasantafe.ppc.presenter.Routes.AUDITOR_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.CONFIG_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.DATE_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.FRONT_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.HARVESTER_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.HEADER_LIST_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.INITIAL_MENU_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.OPERATOR_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.OS_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.PASSWORD_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.PLOT_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.SAMPLE_LIST_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.SECTION_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.SPLASH_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.TURN_HEADER_ROUTE
import br.com.usinasantafe.ppc.presenter.Routes.FIELD_SAMPLE_ROUTE
import br.com.usinasantafe.ppc.presenter.view.configuration.config.ConfigScreen
import br.com.usinasantafe.ppc.presenter.view.configuration.initial.InitialMenuScreen
import br.com.usinasantafe.ppc.presenter.view.configuration.password.PasswordScreen
import br.com.usinasantafe.ppc.presenter.view.header.auditor.AuditorScreen
import br.com.usinasantafe.ppc.presenter.view.header.date.DateScreen
import br.com.usinasantafe.ppc.presenter.view.header.front.FrontScreen
import br.com.usinasantafe.ppc.presenter.view.header.harvester.HarvesterScreen
import br.com.usinasantafe.ppc.presenter.view.header.headerlist.HeaderListScreen
import br.com.usinasantafe.ppc.presenter.view.header.operator.OperatorScreen
import br.com.usinasantafe.ppc.presenter.view.header.os.OSScreen
import br.com.usinasantafe.ppc.presenter.view.header.plot.PlotScreen
import br.com.usinasantafe.ppc.presenter.view.header.section.SectionScreen
import br.com.usinasantafe.ppc.presenter.view.header.turn.TurnScreen
import br.com.usinasantafe.ppc.presenter.view.sample.samplelist.SampleListScreen
import br.com.usinasantafe.ppc.presenter.view.splash.SplashScreen
import br.com.usinasantafe.ppc.presenter.view.sample.field.FieldScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = SPLASH_ROUTE,
    navActions: NavigationActions = remember(navHostController) {
        NavigationActions(navHostController)
    }
) {

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        ///////////////////////// Splash //////////////////////////////////

        composable(SPLASH_ROUTE) {
            SplashScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////


        ///////////////////////// Config //////////////////////////////////

        composable(INITIAL_MENU_ROUTE) {
            InitialMenuScreen(
                onNavPassword = {
                    navActions.navigateToPassword()
                },
                onNavHeaderList = {
                    navActions.navigateToHeaderList()
                }
            )
        }

        composable(PASSWORD_ROUTE) {
            PasswordScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                },
                onNavConfig = {
                    navActions.navigateToConfig()
                }
            )
        }

        composable(CONFIG_ROUTE)  {
            ConfigScreen(
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////// Header //////////////////////////////////

        composable(HEADER_LIST_ROUTE) {
            HeaderListScreen(
                onNavAuditor = {
                    navActions.navigateToAuditorHeader()
                },
                onNavSampleList = {
                    navActions.navigateToSampleList()
                },
                onNavInitialMenu = {
                    navActions.navigateToInitialMenu()
                }
            )
        }

        composable(AUDITOR_HEADER_ROUTE) {
            AuditorScreen(
                onNavHeaderList = {
                    navActions.navigateToHeaderList()
                },
                onNavDate = {
                    navActions.navigateToDateHeader()
                }
            )
        }

        composable(DATE_HEADER_ROUTE) {
            DateScreen(
                onNavAuditor = {
                    navActions.navigateToAuditorHeader()
                },
                onNavTurn = {
                    navActions.navigateToTurnHeader()
                }
            )
        }

        composable(TURN_HEADER_ROUTE) {
            TurnScreen(
                onNavDate = {
                    navActions.navigateToDateHeader()
                },
                onNavOS = {
                    navActions.navigateToOSHeader()
                }
            )
        }

        composable(OS_HEADER_ROUTE) {
            OSScreen(
                onNavTurn = {
                    navActions.navigateToTurnHeader()
                },
                onNavSection = {
                    navActions.navigateToSectionHeader()
                }
            )
        }

        composable(SECTION_HEADER_ROUTE) {
            SectionScreen(
                onNavOS = {
                    navActions.navigateToOSHeader()
                },
                onNavPlot = {
                    navActions.navigateToPlotHeader()
                }
            )
        }

        composable(PLOT_HEADER_ROUTE) {
            PlotScreen(
                onNavSection = {
                    navActions.navigateToSectionHeader()
                },
                onNavFront = {
                    navActions.navigateToFrontHeader()
                }
            )
        }

        composable(FRONT_HEADER_ROUTE) {
            FrontScreen(
                onNavPlot = {
                    navActions.navigateToPlotHeader()
                },
                onNavHarvester = {
                    navActions.navigateToHarvesterHeader()
                }
            )
        }

        composable(HARVESTER_HEADER_ROUTE) {
            HarvesterScreen(
                onNavFront = {
                    navActions.navigateToFrontHeader()
                },
                onNavOperator = {
                    navActions.navigateToOperatorHeader()
                }
            )
        }

        composable(OPERATOR_HEADER_ROUTE) {
            OperatorScreen(
                onNavHarvester = {
                    navActions.navigateToHarvesterHeader()
                },
                onNavHeaderList = {
                    navActions.navigateToHeaderList()
                }
            )
        }

        ////////////////////////////////////////////////////////////////////

        ////////////////////////// Sample //////////////////////////////////

        composable(SAMPLE_LIST_ROUTE) {
            SampleListScreen(
                onNavHeaderList = {
                    navActions.navigateToHeaderList()
                },
                onNavFieldSample = {
                    navActions.navigateToFieldSample()
                }
            )
        }

        composable(
            FIELD_SAMPLE_ROUTE,
            arguments = listOf(
                navArgument(CHECK_OPEN_SAMPLE_ARGS) {
                    type = NavType.BoolType
                }
            )
        ) {
            FieldScreen(
                onNavSampleList = {
                    navActions.navigateToSampleList()
                },
                onNavObsList = {
                }
            )
        }

        ////////////////////////////////////////////////////////////////////


    }

}