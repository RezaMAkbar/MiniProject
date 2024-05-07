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
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Warning
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
fun WeightScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.calc_weight_top))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_purple),
                    titleContentColor = Color.White
                ),
                actions = {
                    val items = listOf(
                        stringResource(id = R.string.home),
                        stringResource(id = R.string.konversi_suhu),
                        stringResource(id = R.string.konversi_panjang),
                        stringResource(id = R.string.konversi_kecepatan),
                        stringResource(id = R.string.bmi),
                        stringResource(id = R.string.about_app),
                    )
                    val screens = listOf(
                        Screen.Home,
                        Screen.Temp,
                        Screen.Length,
                        Screen.Speed,
                        Screen.Bmi,
                        Screen.About
                    )

                    var expandedTopMenu by rememberSaveable { mutableStateOf(false) }
                    var selectedIndexTopMenu by rememberSaveable { mutableIntStateOf(0) }
                    Row(
                        modifier = Modifier.clickable(onClick = { expandedTopMenu = true }),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = stringResource(id = R.string.app_desc),
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                    }

                    DropdownMenu(
                        expanded = expandedTopMenu,
                        onDismissRequest = { expandedTopMenu = false }
                    ) {
                        items.forEachIndexed { index, item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedIndexTopMenu = index
                                    expandedTopMenu = false
                                    navController.navigate(screens[index].route)
                                },
                                leadingIcon = {
                                    when (index) {
                                        0 -> Icon(
                                            Icons.Outlined.Home,
                                            contentDescription = null
                                        )
                                        1 -> Icon(
                                            painter = painterResource(R.drawable.temp),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        2 -> Icon(
                                            painter = painterResource(R.drawable.length),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        3 -> Icon(
                                            painter = painterResource(R.drawable.speed),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        4 -> Text(
                                            text = stringResource(R.string.bmi),
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        5 -> Icon(
                                            imageVector = Icons.Outlined.Info,
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )

                                        else -> Icon(Icons.Outlined.Warning, contentDescription = "Missing Icon")
                                    }
                                })
                        }
                    }
                }
            )
        }
    ) { padding -> WeightContent(Modifier.padding(padding)) }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun WeightPreview() {
    MiniProjectTheme {
        WeightScreen(rememberNavController())
    }
}

@Composable
fun WeightContent(modifier: Modifier) {
    var weight1 by rememberSaveable { mutableStateOf("") }
    var weight1Error by rememberSaveable { mutableStateOf(false) }

    var weightResult by rememberSaveable { mutableStateOf("") }

    val items = listOf(
        stringResource(id = R.string.weight_gram),
        stringResource(id = R.string.weight_kgram),
        stringResource(id = R.string.weight_pound),
        stringResource(id = R.string.weight_us_ton),
        stringResource(id = R.string.weight_ton),
    )

    var expanded by rememberSaveable { mutableStateOf(false) }
    var expanded2 by rememberSaveable { mutableStateOf(false) }
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    var selectedIndex2 by rememberSaveable { mutableIntStateOf(0) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.calc_weight_main),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp)
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
                        )
                }
            }
        }

        OutlinedTextField(
            value = weight1,
            onValueChange = { weight1 = it },
            label = { Text(text = stringResource(id = R.string.from)) },
            trailingIcon = { IconPickerWeight(weight1Error, "") },
            supportingText = { ErrorHintWeight(weight1Error) },
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
                .padding(top = 16.dp)
                .offset(x = 60.dp)
        ) {
            Button(
                onClick = {
                    weight1Error = (weight1 == "")
                    if(weight1Error) return@Button

                    if (selectedIndex == 0 && selectedIndex2 == 0) {
                        weightResult = weight1
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 1) {
                        weightResult = gramToKilogram(weight1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 2) {
                        weightResult = gramToPound(weight1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 3) {
                        weightResult = gramToTonne(weight1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 4) {
                        weightResult = gramToShortTon(weight1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 0) {
                        weightResult = kilogramToGram(weight1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 1) {
                        weightResult = weight1
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 2) {
                        weightResult = kilogramToPound(weight1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 3) {
                        weightResult = kilogramToTonne(weight1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 4) {
                        weightResult = kilogramToShortTon(weight1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 0) {
                        weightResult = poundToGram(weight1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 1) {
                        weightResult = poundToKilogram(weight1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 2) {
                        weightResult = weight1
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 3) {
                        weightResult = poundToTonne(weight1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 4) {
                        weightResult = poundToShortTon(weight1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 0) {
                        weightResult = shortTonToGram(weight1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 1) {
                        weightResult = shortTonToKilogram(weight1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 2) {
                        weightResult = shortTonToPound(weight1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 3) {
                        weightResult = weight1
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 4) {
                        weightResult = shortTonToTonne(weight1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 0) {
                        weightResult = tonneToGram(weight1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 1) {
                        weightResult = tonneToKilogram(weight1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 2) {
                        weightResult = tonneToPound(weight1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 3) {
                        weightResult = tonneToShortTon(weight1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 4) {
                        weightResult = weight1
                    }
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.convert))
            }
            Button(
                onClick = { weight1 = ""; weightResult = "" },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp, start = 15.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.reset))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            Row(
                modifier = Modifier.clickable(onClick = { expanded2 = true }),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = items[selectedIndex2])
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Expand dropdown")
            }
            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false }
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = { selectedIndex2 = index
                            expanded2 = false }
                    )
                }
            }
        }
        OutlinedTextField(
            value = weightResult,
            readOnly = true,
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.to)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun IconPickerWeight(isError : Boolean, unit : String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHintWeight(isError : Boolean){
    if (isError) {
        Text(text = stringResource(id = R.string.invalid_input))
    }
}

private fun gramToKilogram(weight1: Float): String {
    return (weight1 / 1000).toString()
}

private fun gramToPound(weight1: Float): String {
    return (weight1 / 453.6).toString()
}

private fun gramToTonne(weight1: Float): String {
    return (weight1 / 1000000).toString()
}

private fun gramToShortTon(weight1: Float): String {
    return (weight1 / 907200).toString()
}

private fun kilogramToGram(weight1: Float): String {
    return (weight1 * 1000).toString()
}

private fun kilogramToPound(weight1: Float): String {
    return (weight1 * 2.205).toString()
}

private fun kilogramToTonne(weight1: Float): String {
    return (weight1 / 1000).toString()
}

private fun kilogramToShortTon(weight1: Float): String {
    return (weight1 / 907.2).toString()
}

private fun poundToGram(weight1: Float): String {
    return (weight1 * 453.6).toString()
}

private fun poundToKilogram(weight1: Float): String {
    return (weight1 / 2.205).toString()
}

private fun poundToTonne(weight1: Float): String {
    return (weight1 / 2205).toString()
}

private fun poundToShortTon(weight1: Float): String {
    return (weight1 / 2000).toString()
}

private fun tonneToGram(weight1: Float): String {
    return (weight1 * 1000000).toString()
}

private fun tonneToKilogram(weight1: Float): String {
    return (weight1 * 1000).toString()
}

private fun tonneToPound(weight1: Float): String {
    return (weight1 * 2205).toString()
}

private fun tonneToShortTon(weight1: Float): String {
    return (weight1 * 1.102).toString()
}

private fun shortTonToGram(weight1: Float): String {
    return (weight1 * 907200).toString()
}

private fun shortTonToKilogram(weight1: Float): String {
    return (weight1 * 907.2).toString()
}

private fun shortTonToPound(weight1: Float): String {
    return (weight1 * 2000).toString()
}

private fun shortTonToTonne(weight1: Float): String {
    return (weight1 / 1.102).toString()
}