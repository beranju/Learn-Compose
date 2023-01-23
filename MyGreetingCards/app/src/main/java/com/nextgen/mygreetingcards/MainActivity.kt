package com.nextgen.mygreetingcards

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextgen.mygreetingcards.ui.theme.MyGreetingCardsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGreetingCardsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background) {
                    BirthdayGreetingWithImage(message = getString(R.string.love_text), form = "-beranju")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Magenta) {
        Text(text = "Hello, my name is $name!", Modifier.padding(24.dp))
    }
}

@Composable
fun FancyButton(text: String) {
    Text(text = text)
}

@Composable
fun BirthdayGreetingWithText(message: String, form: String){
//    Row {
//        Text(
//            text = message,
//            fontSize = 36.sp,
//        )
//        Text(text = form,
//            fontSize = 24.sp
//        )
//    }
    Column {
        Text(
            text = message,
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp)
        )
        Text(text = form,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun BirthdayGreetingWithImage(message: String, form: String){
    val image = painterResource(id = R.drawable.androidparty)
    //set image and text in box
    Box {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
        BirthdayGreetingWithText(message = message, form = form)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyGreetingCardsTheme {
        BirthdayGreetingWithImage(message = "love u ayg lidia", "-from beranju")
    }
}