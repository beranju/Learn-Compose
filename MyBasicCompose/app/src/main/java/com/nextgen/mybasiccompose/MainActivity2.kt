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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nextgen.mybasiccompose.ui.theme.MyBasicComposeTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBasicComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background) {
                    ComposeTaskApp()
                }
            }
        }
    }
}

@Composable
fun ComposeTaskApp(){
    TaskStatusCard(
        image = painterResource(id = R.drawable.ic_task_completed),
        status = stringResource(R.string.task_status),
        description = stringResource(R.string.task_description)
    )
}

@Composable
fun TaskStatusCard(image: Painter, status: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(painter = image, contentDescription = null)
        Text(
            text = status,
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = description,
            fontSize = 16.sp
        )


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface {
        ComposeTaskApp()
    }
}