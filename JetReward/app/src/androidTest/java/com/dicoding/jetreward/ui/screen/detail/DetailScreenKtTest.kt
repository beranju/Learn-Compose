package com.dicoding.jetreward.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import com.dicoding.jetreward.R
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.dicoding.jetreward.model.OrderReward
import com.dicoding.jetreward.model.Reward
import com.dicoding.jetreward.onNodeWithStringId
import com.dicoding.jetreward.ui.theme.JetRewardTheme
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest{

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOrderReward = OrderReward(
        reward = Reward(id = 4, image = R.drawable.reward_4, title = "Jaket Hodie Dicoding", requiredPoint = 4000),
        count = 0
    )

    @Before
    fun setUp(){
        composeRule.setContent {
            JetRewardTheme {
                DetailContent(
                    image = fakeOrderReward.reward.image,
                    title = fakeOrderReward.reward.title,
                    basePoint = fakeOrderReward.reward.requiredPoint,
                    count = fakeOrderReward.count,
                    onBackClick = {},
                    onAddToCart ={},
                )
            }
        }
        composeRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed(){
        composeRule.onNodeWithText(fakeOrderReward.reward.title).assertIsDisplayed()
        composeRule.onNodeWithText(
            composeRule.activity.getString(
                R.string.required_point,
                fakeOrderReward.reward.requiredPoint
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_btnIsEnabled(){
        composeRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
//        composeRule.onNodeWithText(
//            composeRule.activity.getString(R.string.plus_symbol)
//        ).performClick()
        //use extention function to concise code
        composeRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_correctCounter(){
        composeRule.onNodeWithStringId(R.string.plus_symbol).performClick().performClick()
        composeRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_stillZero(){
        composeRule.onNodeWithStringId(R.string.minus_symbol).performClick()
        composeRule.onNodeWithTag("count").assert(hasText("0"))
    }
}