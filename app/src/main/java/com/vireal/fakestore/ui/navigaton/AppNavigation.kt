package com.vireal.fakestore.ui.navigaton

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vireal.fakestore.ui.itemDetails.ItemDetailsScreen
import com.vireal.fakestore.ui.itemlist.ItemListScreen

// Define route constants
object AppRoutes {
  const val ITEM_LIST = "itemList"
  const val ITEM_DETAILS = "itemDetails/{itemId}"

  // Functions to create routes with parameters
  fun itemDetails(itemId: Int): String = "itemDetails/$itemId"
}

@Composable
fun AppNavigation(
  navController: NavHostController = rememberNavController(),
  startDestination: String = AppRoutes.ITEM_LIST
) {
  NavHost(
    navController = navController,
    startDestination = startDestination
  ) {
    // ItemList screen
    composable(route = AppRoutes.ITEM_LIST) {
      ItemListScreen(
        onNavigateToDetail = { itemId ->
          navController.navigate(AppRoutes.itemDetails(itemId))
        }
      )
    }

    // ItemDetails screen
    composable(
      route = AppRoutes.ITEM_DETAILS,
      arguments = listOf(
        navArgument("itemId") {
          type = NavType.IntType
        }
      )
    ) { backStackEntry ->
      val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
      ItemDetailsScreen(
        itemId = itemId,
        onNavigateBack = {
          navController.popBackStack()
        }
      )
    }
  }
}