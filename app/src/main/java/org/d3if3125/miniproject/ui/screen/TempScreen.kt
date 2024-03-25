package org.d3if3125.miniproject.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3125.miniproject.R
import org.d3if3125.miniproject.navigation.Screen
import org.d3if3125.miniproject.ui.theme.MiniProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.calc_temp_top))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_purple),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.About.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(id = R.string.app_desc),
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding -> TemperatureContent(Modifier.padding(padding)) }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TemperaturePreview() {
    MiniProjectTheme {
        TemperatureScreen(rememberNavController())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemperatureContent(modifier: Modifier) {
    var temp1 by rememberSaveable { mutableStateOf("") }
    var temp1Error by rememberSaveable { mutableStateOf(false) }

    var tempResult by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    val items = listOf(
        stringResource(id = R.string.temp_celcius),
        stringResource(id = R.string.temp_fahrenheit),
        stringResource(id = R.string.temp_kelvin),
        stringResource(id = R.string.temp_rankine),
    )
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.calc_temp_main),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 36.dp)
        ) {
            Row(
                modifier = Modifier.clickable(onClick = { expanded = true }),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = items[selectedIndex])
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Expand dropdown")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { selectedIndex = index
                            expanded = false },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Edit,
                                contentDescription = null
                            )
                        })
                }
            }
        }

        OutlinedTextField(
            value = temp1,
            onValueChange = { temp1 = it },
            label = { Text(text = "")},
            trailingIcon = { IconPicker(temp1Error, "") },
            supportingText = { ErrorHint(temp1Error) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .offset(x = 60.dp)
        ) {
            Button(
                onClick = {  },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.convert))
            }
            Button(
                onClick = { temp1 = ""; tempResult = "" },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 15.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
        }
        OutlinedTextField(
            value = tempResult,
            readOnly = true,
            onValueChange = {  },
            label = { Text(text = "")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun IconPicker(isError : Boolean, unit : String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError : Boolean){
    if (isError) {
        Text(text = stringResource(id = R.string.invalid_input))
    }
}