package com.zk.trackshows.ui.main


//sealed class AppScreens(val route: String) {
//    object Main : AppScreens("Main")
//    object Details : AppScreens("Details")
//    object Search : AppScreens("Search")
//}
////
//@FlowPreview
//@ExperimentalCoroutinesApi
//@ExperimentalAnimationApi
//@Composable
//fun TrackShowsApp(viewModel: MainViewModel) {
//
//    val navController = rememberNavController()
//    navigationConfigurations(navController, viewModel, initialScreen = AppScreens.Main.route) // Declare the navigation flows
//
//
//}
//
//@ExperimentalCoroutinesApi
//@FlowPreview
//@ExperimentalAnimationApi
//@Composable
//private fun navigationConfigurations(
//
//    navController: NavHostController,
//    viewModel: MainViewModel,
//    initialScreen: String
//) {
//    NavHost(navController, startDestination = initialScreen) {
//        composable(AppScreens.Main.route) {
//            MainScreen(viewModel) {
//                handleInteractionEvents(navController = navController, interactionEvents = it)
//            }
//        }
//
//        composable("Details",
//            arguments = listOf(navArgument("show") {
//                type = NavType.IntType
//            })
//        ) { backStackEntry ->
//            ShowDetails(viewModel)
//        }
//        composable(AppScreens.Search.route) {
//            SearchScreen(viewModel)
//        }
//    }
//}
//
