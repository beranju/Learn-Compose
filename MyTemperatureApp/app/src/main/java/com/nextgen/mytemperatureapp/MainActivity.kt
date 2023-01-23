package com.nextgen.mytemperatureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nextgen.mytemperatureapp.ui.theme.MyTemperatureAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTemperatureAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        StatefulTemperatureInput()
                        ConverterApp()
                        TwoWayConverterApp()
                    }
                }
            }
        }
    }
}

@Composable
fun StatefulTemperatureInput(
    modifier: Modifier = Modifier
) {
    var input by remember {
        mutableStateOf("")
    }
    var output by remember {
        mutableStateOf("")
    }
    Column(
        modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateful_converter),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = input,
            label = {
                    Text(text = stringResource(id = R.string.enter_celsius))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange ={newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            },
        )
        Text(
            text = stringResource(id = R.string.temperature_fahrenheit, output) 
        )
    }
}

@Composable
fun StatelessTemperatureInput(
    input: String,
    output: String,
    onchange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.stateless_converter),
            style = MaterialTheme.typography.h5,
        )
        OutlinedTextField(
            value = input,
            label = {
                    Text(text = stringResource(id = R.string.enter_celsius))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) ,
            onValueChange = onchange,
        )
        Text(text = stringResource(id = R.string.temperature_fahrenheit, output))
    }
}

@Composable
fun ConverterApp(
    modifier: Modifier = Modifier
) {
    var input by remember {
        mutableStateOf("")
    }
    var output by remember {
        mutableStateOf("")
    }
    Column(modifier) {
        StatelessTemperatureInput(
            input = input,
            output = output,
            onchange = {newInput ->
                input = newInput
                output = convertToFahrenheit(newInput)
            } )
    }
}

@Composable
fun GeneralTemperatureInput(
    scale: Scale,
    input: String,
    onchange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
    ) {
        OutlinedTextField(
            value = input,
            label = {
                    Text(text = stringResource(id = R.string.enter_temperature, scale.scaleName))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onchange)

    }

}

@Composable
fun TwoWayConverterApp(
    modifier: Modifier = Modifier
) {
    var celsius by remember {
        mutableStateOf("")
    }
    var fahrenheit by remember {
        mutableStateOf("")
    }
    Column(modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.two_way_converter),
            style = MaterialTheme.typography.h5
        )
        GeneralTemperatureInput(
            scale = Scale.CELSIUS ,
            input = celsius,
            onchange = {newInput ->
                celsius = newInput
                fahrenheit = convertToFahrenheit(newInput)
            }
        )
        GeneralTemperatureInput(
            scale = Scale.FAHRENHEIT,
            input = fahrenheit,
            onchange = {newInput ->
                fahrenheit = newInput
                celsius = convertToCelcius(newInput)
            })
    }

}

fun convertToCelcius(newInput: String): String =
    newInput.toDoubleOrNull()?.let {
        (it - 32 )* 5 / 9
    }.toString()

fun convertToFahrenheit(newInput: String): String =
    newInput.toDoubleOrNull()?.let {
        (it * 9 / 5) + 32
    }.toString()


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyTemperatureAppTheme {
        Column {
            StatefulTemperatureInput()
            ConverterApp()
            TwoWayConverterApp()
        }
    }
}

enum class Scale(val scaleName: String){
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit")
}