package com.nextgen.mynavdrawer

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextgen.mynavdrawer.ui.theme.MyNavDrawerTheme
import kotlinx.coroutines.launch

@Composable
fun MyNavDrawerApp() {
    //diganti dengan state yg dibuat
//    val scaffoldState = rememberScaffoldState()
//    val scope = rememberCoroutineScope()
//    val context = LocalContext.current
    //state yang dibuat
    val appState = rememberMyNavDrawerState()
    val activity = (LocalContext.current as Activity)

    Scaffold (
        scaffoldState = appState.scaffoldState,
        topBar = {
            MyTopBar(
//                onMenuClick = { appState.onMenuClick() }
            //diganti dengan function reference
                onMenuClick = appState::onMenuClick
            )
        },
        drawerContent = {
            MyDrawerContent(
                onItemSelected = appState::onSelectedItem,
                onBackPressed = appState::onBackPressed
            )
        },
        drawerGesturesEnabled = appState.scaffoldState.drawerState.isOpen,
    ){innerPadding->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ){
            Text(text = stringResource(id = R.string.hello_world))
        }
        
    }
}

@Composable
fun MyDrawerContent(
    modifier: Modifier = Modifier,
    onItemSelected: (title: String) -> Unit,
    onBackPressed: () -> Unit,
) {
    val items = listOf(
        MenuItem(
            title = stringResource(id = R.string.home),
            menuIcon = Icons.Default.Home,
        ),
        MenuItem(
            title = stringResource(id = R.string.favourite),
            menuIcon = Icons.Default.Favorite,
        ),
        MenuItem(
            title = stringResource(id = R.string.profile),
            menuIcon = Icons.Default.AccountCircle,
        ),
    )
    
    Column(modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        )
        for (item in items){
            Row(
                modifier = Modifier
                    .clickable { onItemSelected(item.title) }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.menuIcon,
                    contentDescription = item.title,
                    tint = Color.DarkGray
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
        Divider()
        
    }
    BackPressHandler{
        onBackPressed()
    }


}

@Composable
fun BackPressHandler(enabled: Boolean = true, onBackPressed: () -> Unit) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(enabled){
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current){
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher){
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose{
            backCallback.remove()
        }
    }



}

@Composable
fun MyTopBar(onMenuClick: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = {
                    onMenuClick()
                }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            }
        },
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MyNavDrawerAppPreview() {
    MyNavDrawerTheme {
        MyNavDrawerApp()
    }
}