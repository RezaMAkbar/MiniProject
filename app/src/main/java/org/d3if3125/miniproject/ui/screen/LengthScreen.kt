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
fun LengthScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.calc_length_top))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_purple),
                    titleContentColor = Color.White
                ),
                actions = {
                    val items = listOf(
                        stringResource(id = R.string.home),
                        stringResource(id = R.string.konversi_suhu),
                        stringResource(id = R.string.konversi_berat),
                        stringResource(id = R.string.konversi_kecepatan),
                        stringResource(id = R.string.about_app),
                    )
                    val screens = listOf(
                        Screen.Home,
                        Screen.Temp,
                        Screen.Weight,
                        Screen.Speed,
                        Screen.About,
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
                                            painter = painterResource(R.drawable.weight),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        3 -> Icon(
                                            painter = painterResource(R.drawable.speed),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        4 -> Icon(
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
    ) { padding -> LengthContent(Modifier.padding(padding)) }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun LengthPreview() {
    MiniProjectTheme {
        LengthScreen(rememberNavController())
    }
}

@Composable
fun LengthContent(modifier: Modifier) {
    var length by rememberSaveable { mutableStateOf("") }
    var lengthError by rememberSaveable { mutableStateOf(false) }

    var lengthResult by rememberSaveable { mutableStateOf("") }

    val items = listOf(
        stringResource(id = R.string.length_km),
        stringResource(id = R.string.length_metre),
        stringResource(id = R.string.length_cm),
        stringResource(id = R.string.length_mm),
        stringResource(id = R.string.length_mile),
        stringResource(id = R.string.length_yard),
        stringResource(id = R.string.length_foot),
        stringResource(id = R.string.length_inch),
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
            text = stringResource(id = R.string.calc_length_main),
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
                            expanded = false })
                }
            }
        }

        OutlinedTextField(
            value = length,
            onValueChange = { length = it },
            label = { Text(text = "From")},
            trailingIcon = { IconPickerLength(lengthError, "") },
            supportingText = { ErrorHintLength(lengthError) },
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
                    lengthError = (length == "")
                    if(lengthError) return@Button

                    if (selectedIndex == 0 && selectedIndex2 == 0) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 1) {
                        lengthResult = kmToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 2) {
                        lengthResult = kmToCm(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 3) {
                        lengthResult = kmToMm(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 4) {
                        lengthResult = kmToMile(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 5) {
                        lengthResult = kmToYard(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 6) {
                        lengthResult = kmToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 0 && selectedIndex2 == 7) {
                        lengthResult = kmToInch(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 0) {
                        lengthResult = metreToKm(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 1) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 2) {
                        lengthResult = metreToCm(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 3) {
                        lengthResult = metreToMm(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 4) {
                        lengthResult = metreToMile(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 5) {
                        lengthResult = metreToYard(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 6) {
                        lengthResult = metreToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 1 && selectedIndex2 == 7) {
                        lengthResult = metreToInch(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 0) {
                        lengthResult = cmToKm(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 1) {
                        lengthResult = cmToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 2) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 3) {
                        lengthResult = cmToMm(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 4) {
                        lengthResult = cmToMile(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 5) {
                        lengthResult = cmToYard(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 6) {
                        lengthResult = cmToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 2 && selectedIndex2 == 7) {
                        lengthResult = cmToInch(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 0) {
                        lengthResult = mmToKm(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 1) {
                        lengthResult = mmToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 2) {
                        lengthResult = mmToCm(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 3) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 4) {
                        lengthResult = mmToMile(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 5) {
                        lengthResult = mmToYard(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 6) {
                        lengthResult = mmToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 3 && selectedIndex2 == 7) {
                        lengthResult = mmToInch(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 0) {
                        lengthResult = mileToKm(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 1) {
                        lengthResult = mileToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 2) {
                        lengthResult = mileToCm(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 3) {
                        lengthResult = mileToMm(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 4) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 5) {
                        lengthResult = mileToYard(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 6) {
                        lengthResult = mileToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 4 && selectedIndex2 == 7) {
                        lengthResult = mileToInch(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 0) {
                        lengthResult = yardToKm(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 1) {
                        lengthResult = yardToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 2) {
                        lengthResult = yardToCm(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 3) {
                        lengthResult = yardToMm(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 4) {
                        lengthResult = yardToMile(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 5) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 6) {
                        lengthResult = yardToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 5 && selectedIndex2 == 7) {
                        lengthResult = yardToInch(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 0) {
                        lengthResult = footToKm(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 1) {
                        lengthResult = footToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 2) {
                        lengthResult = footToCm(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 3) {
                        lengthResult = footToMm(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 4) {
                        lengthResult = footToMile(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 5) {
                        lengthResult = footToYard(length.toFloat())
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 6) {
                        lengthResult = length
                    }
                    else if (selectedIndex == 6 && selectedIndex2 == 7) {
                        lengthResult = footToInch(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 0) {
                        lengthResult = inchToKm(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 1) {
                        lengthResult = inchToMetre(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 2) {
                        lengthResult = inchToCm(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 3) {
                        lengthResult = inchToMm(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 4) {
                        lengthResult = inchToMile(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 5) {
                        lengthResult = inchToYard(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 6) {
                        lengthResult = inchToFoot(length.toFloat())
                    }
                    else if (selectedIndex == 7 && selectedIndex2 == 7) {
                        lengthResult = length
                    }
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.convert))
            }
            Button(
                onClick = { length = ""; lengthResult = "" },
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
            value = lengthResult,
            readOnly = true,
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.to)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun IconPickerLength(isError : Boolean, unit : String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHintLength(isError : Boolean){
    if (isError) {
        Text(text = stringResource(id = R.string.invalid_input))
    }
}

private fun metreToKm(length: Float): String {
    return (length / 1000).toString()
}

private fun metreToCm(length: Float): String {
    return (length * 100).toString()
}

private fun metreToFoot(length: Float): String {
    return (length * 3.281).toString()
}

private fun metreToInch(length: Float): String {
    return (length * 39.37).toString()
}

private fun metreToMile(length: Float): String {
    return (length / 1609).toString()
}

private fun metreToMm(length: Float): String {
    return (length * 1000).toString()
}

private fun metreToYard(length: Float): String {
    return (length * 1.094).toString()
}

private fun kmToMetre(length: Float): String {
    return (length * 1000).toString()
}

private fun kmToCm(length: Float): String {
    return (length * 100000).toString()
}

private fun kmToMm(length: Float): String {
    return (length * 1000000).toString()
}

private fun kmToMile(length: Float): String {
    return (length / 1.609).toString()
}

private fun kmToYard(length: Float): String {
    return (length * 1094).toString()
}

private fun kmToFoot(length: Float): String {
    return (length * 3281).toString()
}

private fun kmToInch(length: Float): String {
    return (length * 39370).toString()
}

private fun cmToKm(length: Float): String {
    return (length / 100000).toString()
}

private fun cmToMetre(length: Float): String {
    return (length / 100).toString()
}

private fun cmToMm(length: Float): String {
    return (length * 10).toString()
}

private fun cmToMile(length: Float): String {
    return (length / 160900).toString()
}

private fun cmToYard(length: Float): String {
    return (length / 91.44).toString()
}

private fun cmToFoot(length: Float): String {
    return (length / 30.48).toString()
}

private fun cmToInch(length: Float): String {
    return (length / 2.54).toString()
}

private fun mmToKm(length: Float): String {
    return (length / 1000000).toString()
}

private fun mmToMetre(length: Float): String {
    return (length / 1000).toString()
}

private fun mmToCm(length: Float): String {
    return (length / 10).toString()
}

private fun mmToMile(length: Float): String {
    return (length / 1609000).toString()
}

private fun mmToYard(length: Float): String {
    return (length / 914.4).toString()
}

private fun mmToFoot(length: Float): String {
    return (length / 304.8).toString()
}

private fun mmToInch(length: Float): String {
    return (length / 25.4).toString()
}

private fun mileToKm(length: Float): String {
    return (length * 1.609).toString()
}

private fun mileToMetre(length: Float): String {
    return (length * 1609).toString()
}

private fun mileToCm(length: Float): String {
    return (length * 160900).toString()
}

private fun mileToMm(length: Float): String {
    return (length * 1609000).toString()
}

private fun mileToYard(length: Float): String {
    return (length * 1760).toString()
}

private fun mileToFoot(length: Float): String {
    return (length * 5280).toString()
}

private fun mileToInch(length: Float): String {
    return (length * 63360).toString()
}

private fun yardToKm(length: Float): String {
    return (length / 1094).toString()
}

private fun yardToMetre(length: Float): String {
    return (length / 1.094).toString()
}

private fun yardToCm(length: Float): String {
    return (length * 91.44).toString()
}

private fun yardToMm(length: Float): String {
    return (length * 914.4).toString()
}

private fun yardToMile(length: Float): String {
    return (length / 1760).toString()
}

private fun yardToFoot(length: Float): String {
    return (length * 3).toString()
}

private fun yardToInch(length: Float): String {
    return (length * 36).toString()
}

private fun footToKm(length: Float): String {
    return (length / 3281).toString()
}

private fun footToMetre(length: Float): String {
    return (length / 3.281).toString()
}

private fun footToCm(length: Float): String {
    return (length * 30.48).toString()
}

private fun footToMm(length: Float): String {
    return (length * 304.8).toString()
}

private fun footToMile(length: Float): String {
    return (length / 5280).toString()
}

private fun footToYard(length: Float): String {
    return (length / 3).toString()
}


private fun footToInch(length: Float): String {
    return (length * 12).toString()
}

private fun inchToKm(length: Float): String {
    return (length / 39370).toString()
}

private fun inchToMetre(length: Float): String {
    return (length / 39.37).toString()
}

private fun inchToCm(length: Float): String {
    return (length * 2.54).toString()
}

private fun inchToMm(length: Float): String {
    return (length * 25.4).toString()
}

private fun inchToMile(length: Float): String {
    return (length / 63360).toString()
}

private fun inchToYard(length: Float): String {
    return (length / 36).toString()
}

private fun inchToFoot(length: Float): String {
    return (length / 12).toString()
}