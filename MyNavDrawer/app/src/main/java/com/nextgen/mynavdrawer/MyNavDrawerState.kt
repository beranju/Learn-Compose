package com.nextgen.mynavdrawer

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyNavDrawerState(
    val scaffoldState: ScaffoldState,
    private val scope: CoroutineScope,
    private val context: Context
) {
    fun onMenuClick(){
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }

    fun onSelectedItem(title: String){
        scope.launch {
            scaffoldState.drawerState.close()
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = context.resources.getString(R.string.coming_soon, title),
                actionLabel = context.resources.getString(R.string.subscribe_question)
            )
            if (snackbarResult == SnackbarResult.ActionPerformed){
                Toast.makeText(context, context.resources.getString(R.string.subscribed_info), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onBackPressed(){
        if (scaffoldState.drawerState.isOpen){
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }else{
            (context as Activity).finish()
        }
    }

}

@Composable
fun rememberMyNavDrawerState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current
): MyNavDrawerState =
    remember(scaffoldState, scope, context){
        MyNavDrawerState(scaffoldState, scope, context)
    }
