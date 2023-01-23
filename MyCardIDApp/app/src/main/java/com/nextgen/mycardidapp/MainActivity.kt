package com.nextgen.mycardidapp

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
import androidx.compose.ui.unit.sp
import com.nextgen.mycardidapp.ui.theme.MyCardIDAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCardIDAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background) {
                    ComposeCardID()
                }
            }
        }
    }
}

@Composable
fun ComposeCardID(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MainCardId(
            logo = painterResource(id = R.drawable.logo_android),
            name = stringResource(R.string.name),
            job = stringResource(R.string.job)
        )

        Box{

            TelpCard(
                icon = painterResource(id = R.drawable.ic_baseline_local_phone_24),
                number = stringResource(R.string.number)

            )
            SocialMediaCard(
                icon = painterResource(id = R.drawable.ic_baseline_camera_24),
                username = stringResource(R.string.username)

            )
            EmailCard(
                icon = painterResource(id = R.drawable.ic_baseline_email_24),
                email = stringResource(R.string.email)

            )
        }

    }
}

@Composable
fun EmailCard(icon: Painter, email: String) {
    Row {
        Image(painter = icon, contentDescription = null)
        Text(text = email)

    }
}

@Composable
fun SocialMediaCard(icon: Painter, username: String) {
    Row {
        Image(painter = icon, contentDescription = null)
        Text(text = username)

    }
}

@Composable
fun TelpCard(icon: Painter, number: String) {
    Row {
        Image(painter = icon, contentDescription = null)
        Text(text = number)
        
    }
}

@Composable
fun MainCardId(logo: Painter, name: String, job: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = logo, contentDescription = "Logo user")
        Text(
            text = name,
            textAlign = TextAlign.Center,
            fontSize = 36.sp
        )
        Text(text = job,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface {
        ComposeCardID()
    }
}