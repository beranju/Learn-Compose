package com.nextgen.mybasiccompose

import android.os.Bundle
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextgen.mybasiccompose.ui.theme.MyBasicComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBasicComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background) {
                    ComposeArticleApp()
                }
            }
        }
    }
}

@Composable
fun ComposeArticleApp(){
    ArticleCard(
        title = stringResource(id = R.string.text_title),
        subTitle = stringResource(id = R.string.text_subtitle),
        description = stringResource(id = R.string.text_content),
        image = painterResource(id = R.drawable.bg_compose_background)
    )
}

@Composable
fun ArticleCard(title: String, subTitle: String, description: String, image: Painter) {
    Column {
        Image(painter = image, contentDescription = null)
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = subTitle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text = description,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MyBasicComposeTheme {
        ComposeArticleApp()
    }
}