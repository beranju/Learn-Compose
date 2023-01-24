package com.dicoding.jetreward

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.testing.TestNavHostController
import com.dicoding.jetreward.model.FakeRewardDataSource
import com.dicoding.jetreward.ui.navigation.Screen
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetRewardAppKtTest{
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navHostController: TestNavHostController

    @Before
    fun setup(){
        composeTestRule.setContent {
            JetRewardTheme {
                navHostController = TestNavHostController(LocalContext.current)
                navHostController.navigatorProvider.addNavigator(ComposeNavigator())
                JetRewardApp(navHostController = navHostController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination(){
//        val currentRoute = navHostController.currentBackStackEntry?.destination?.route
//        assertEquals(Screen.Home.route, currentRoute)

        //use extentions function
        navHostController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigateToDetailWithData(){
        composeTestRule.onNodeWithTag("rewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navHostController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomMenuClick_displayCorrectScreen(){
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navHostController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navHostController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navHostController.assertCurrentRouteName(Screen.Profile.route)
    }

    @Test
    fun navHost_verifyButtonBack(){
        composeTestRule.onNodeWithTag("rewardList").performScrollToIndex(10)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[10].title).performClick()
        navHostController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navHostController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkOut_rightBackStack(){
        composeTestRule.onNodeWithTag("rewardList").performScrollToIndex(4)
        composeTestRule.onNodeWithText(FakeRewardDataSource.dummyRewards[4].title).performClick()
        navHostController.assertCurrentRouteName(Screen.DetailReward.route)
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navHostController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navHostController.assertCurrentRouteName(Screen.Home.route)
    }
}