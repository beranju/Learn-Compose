package com.dicoding.jetreward

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.jetreward.ui.navigation.NavigationItem
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.screen.cart.CartScreen
import com.dicoding.jetreward.ui.screen.detail.DetailScreen
import com.dicoding.jetreward.ui.screen.home.HomeScreen
import com.dicoding.jetreward.ui.screen.profile.ProfileScreen
import com.dicoding.jetreward.ui.theme.JetRewardTheme

@Composable
fun JetRewardApp(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailReward.route){
                BottomNavBar(navHostController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding),
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = {rewardId->
                        navHostController.navigate(Screen.DetailReward.createRoute(rewardId))
                    }
                )
            }
            composable(Screen.Cart.route){
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = {message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen()
            }
            composable(
                route = Screen.DetailReward.route,
                arguments = listOf(
                    navArgument("rewardId"){
                        type = NavType.LongType
                    },
                )
            ){
                val id = it.arguments?.getLong("rewardId") ?: -1L
                DetailScreen(
                    rewardId = id,
                    navigateBack = {
                        navHostController.navigateUp()
                    },
                    navigateToCart = {
                        navHostController.popBackStack()
                        navHostController.navigate(Screen.Cart.route){
                            popUpTo(navHostController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )

            }
        }
    }

}

@Composable
fun BottomNavBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
    ) {
        val navBackStackEntry by navHostController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_cart),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),

        )
        
        BottomNavigation {
            navigationItems.map { item->
                BottomNavigationItem(
                    icon = {
                           Icon(
                               imageVector = item.icon,
                               contentDescription = item.title)
                    },
                    label = {
                            Text(text = item.title)
                    },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navHostController.navigate(item.screen.route){
                            popUpTo(navHostController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

private fun shareOrder(context: Context, summary: String){
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.dicoding_reward))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.dicoding_reward)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetRewardTheme {
        JetRewardApp()
    }
}
