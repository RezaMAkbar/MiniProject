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
import androidx.compose.material.icons.outlined.Menu
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
fun SpeedScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.calc_speed_top))
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
                        stringResource(id = R.string.konversi_panjang),
                        stringResource(id = R.string.bmi),
                        stringResource(id = R.string.note_main_menu),
                        stringResource(id = R.string.about_app),
                    )
                    val screens = listOf(
                        Screen.Home,
                        Screen.Temp,
                        Screen.Weight,
                        Screen.Length,
                        Screen.Bmi,
                        Screen.Note,
                        Screen.About
                    )

                    var expandedTopMenu by rememberSaveable { mutableStateOf(false) }
                    var selectedIndexTopMenu by rememberSaveable { mutableIntStateOf(0) }
                    Row(
                        modifier = Modifier.clickable(onClick = { expandedTopMenu = true }),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
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
                                            painter = painterResource(R.drawable.length),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        4 -> Text(
                                            text = stringResource(R.string.bmi),
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        5 -> Icon(
                                            painter = painterResource(R.drawable.baseline_notes_24),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        6 -> Icon(
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
    ) { padding -> SpeedContent(Modifier.padding(padding)) }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SpeedPreview() {
    MiniProjectTheme {
        SpeedScreen(rememberNavController())
    }
}

@Composable
fun SpeedContent(modifier: Modifier) {
    var speed1 by rememberSaveable { mutableStateOf("") }
    var speed1Error by rememberSaveable { mutableStateOf(false) }

    var speedResult by rememberSaveable { mutableStateOf("") }

    val items = listOf(
        stringResource(id = R.string.speed_metreps),
        stringResource(id = R.string.speed_kph),
        stringResource(id = R.string.speed_mileph),
        stringResource(id = R.string.speed_fps),
        stringResource(id = R.string.speed_knot),
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
            text = stringResource(id = R.string.calc_speed_main),
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
            value = speed1,
            onValueChange = { speed1 = it },
            label = { Text(text = stringResource(id = R.string.from)) },
            trailingIcon = { IconPickerSpeed(speed1Error, "") },
            supportingText = { ErrorHintSpeed(speed1Error) },
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
                    speed1Error = (speed1 == "")
                    if(speed1Error) return@Button

                    if (selectedIndex == 0 && selectedIndex2 == 0) {
                        speedResult = speed1
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 1) {
                        speedResult = meterpsToKmph(speed1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 2) {
                        speedResult = meterpsTomileph(speed1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 3) {
                        speedResult = meterpsToFtps(speed1.toFloat())
                    }
                    if (selectedIndex == 0 && selectedIndex2 == 4) {
                        speedResult = meterpsToKnot(speed1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 0) {
                        speedResult = kmphToMeterps(speed1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 1) {
                        speedResult = speed1
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 2) {
                        speedResult = kmphTomileph(speed1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 3) {
                        speedResult = kmphToFtps(speed1.toFloat())
                    }
                    if (selectedIndex == 1 && selectedIndex2 == 4) {
                        speedResult = kmphToKnot(speed1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 0) {
                        speedResult = milephToMeterps(speed1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 1) {
                        speedResult = milephToKmph(speed1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 2) {
                        speedResult = speed1
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 3) {
                        speedResult = milephToFtps(speed1.toFloat())
                    }
                    if (selectedIndex == 2 && selectedIndex2 == 4) {
                        speedResult = milephToKnot(speed1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 0) {
                        speedResult = ftpsToMeterps(speed1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 1) {
                        speedResult = ftpsToKmph(speed1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 2) {
                        speedResult = ftpsTomileph(speed1.toFloat())
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 3) {
                        speedResult = speed1
                    }
                    if (selectedIndex == 3 && selectedIndex2 == 4) {
                        speedResult = ftpsToKnot(speed1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 0) {
                        speedResult = knotToMeterps(speed1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 1) {
                        speedResult = knotToKmph(speed1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 2) {
                        speedResult = knotTomileph(speed1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 3) {
                        speedResult = knotToFtps(speed1.toFloat())
                    }
                    if (selectedIndex == 4 && selectedIndex2 == 4) {
                        speedResult = speed1
                    }
                },
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 26.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(id = R.string.convert))
            }
            Button(
                onClick = { speed1 = ""; speedResult = "" },
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
            value = speedResult,
            readOnly = true,
            onValueChange = {  },
            label = { Text(text = stringResource(id = R.string.to)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

    }
}

@Composable
fun IconPickerSpeed(isError : Boolean, unit : String){
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHintSpeed(isError : Boolean){
    if (isError) {
        Text(text = stringResource(id = R.string.invalid_input))
    }
}

private fun meterpsToKmph(speed1: Float): String {
    return (speed1 * 3.6).toString()
}

private fun meterpsTomileph(speed1: Float): String {
    return (speed1 * 2.237).toString()
}

private fun meterpsToFtps(speed1: Float): String {
    return (speed1 * 3.281).toString()
}

private fun meterpsToKnot(speed1: Float): String {
    return (speed1 * 1.944).toString()
}

private fun kmphToMeterps(speed1: Float): String {
    return (speed1 / 3.6).toString()
}

private fun kmphTomileph(speed1: Float): String {
    return (speed1 / 1.609).toString()
}

private fun kmphToFtps(speed1: Float): String {
    return (speed1 / 1.097).toString()
}

private fun kmphToKnot(speed1: Float): String {
    return (speed1 / 1.852).toString()
}

private fun milephToKmph(speed1: Float): String {
    return (speed1 * 1.609).toString()
}

private fun milephToMeterps(speed1: Float): String {
    return (speed1 / 2.237).toString()
}

private fun milephToFtps(speed1: Float): String {
    return (speed1 * 1.467).toString()
}

private fun milephToKnot(speed1: Float): String {
    return (speed1 / 1.151).toString()
}

private fun ftpsToKmph(speed1: Float): String {
    return (speed1 * 1.097).toString()
}

private fun ftpsToMeterps(speed1: Float): String {
    return (speed1 / 3.281).toString()
}

private fun ftpsTomileph(speed1: Float): String {
    return (speed1 / 1.467).toString()
}

private fun ftpsToKnot(speed1: Float): String {
    return (speed1 / 1.688).toString()
}

private fun knotToKmph(speed1: Float): String {
    return (speed1 * 1.852).toString()
}

private fun knotToMeterps(speed1: Float): String {
    return (speed1 / 1.944).toString()
}

private fun knotTomileph(speed1: Float): String {
    return (speed1 * 1.151).toString()
}

private fun knotToFtps(speed1: Float): String {
    return (speed1 * 1.688).toString()
}