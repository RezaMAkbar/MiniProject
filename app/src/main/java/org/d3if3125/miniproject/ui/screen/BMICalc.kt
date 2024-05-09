package org.d3if3125.miniproject.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3125.miniproject.R
import org.d3if3125.miniproject.database.NoteDb
import org.d3if3125.miniproject.navigation.Screen
import org.d3if3125.miniproject.ui.theme.MiniProjectTheme
import org.d3if3125.miniproject.util.ViewModelFactory
import kotlin.math.pow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiScreen(navController: NavHostController) {
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
                    Text(text = stringResource(id = R.string.bmi))
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
                        stringResource(id = R.string.konversi_berat),
                        stringResource(id = R.string.note_main_menu),
                        stringResource(id = R.string.about_app),
                    )
                    val screens = listOf(
                        Screen.Home,
                        Screen.Temp,
                        Screen.Length,
                        Screen.Speed,
                        Screen.Weight,
                        Screen.Note,
                        Screen.About,
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
                                            painter = painterResource(R.drawable.length),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        3 -> Icon(
                                            painter = painterResource(R.drawable.speed),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        4 -> Icon(
                                            painter = painterResource(R.drawable.weight),
                                            contentDescription = null,
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
    ) { padding -> BMIContent(Modifier.padding(padding)) }
}

@Composable
fun BMIContent(modifier: Modifier) {
    val context = LocalContext.current
    val db = NoteDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: NoteDetailViewModel = viewModel(factory = factory)

    var weightMetric by remember { mutableStateOf("") }
    var weightImp by remember { mutableStateOf("") }
    var weightErrorMetric by remember { mutableStateOf(false) }
    var weightErrorImp by remember { mutableStateOf(false) }

    var heightMetric by remember { mutableStateOf("") }
    var heightFeet by remember { mutableStateOf("") }
    var heightInch by remember { mutableStateOf("") }
    var heightErrorMetric by remember { mutableStateOf(false) }
    var heightErrorImp by remember { mutableStateOf(false) }

    var clickedOption by remember { mutableStateOf("Metric") }

    var bmi by remember { mutableFloatStateOf(0f) }
    var category by remember { mutableIntStateOf(0) }
    var action by remember { mutableIntStateOf(0) }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.bmi_about),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 26.dp)
        ) {
            Row(
                modifier = Modifier.clickable(onClick = {
                    clickedOption = if (clickedOption == "Metric") "Imperial" else "Metric"
                }),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.metric),
                    style = TextStyle(
                        fontWeight = if (clickedOption == "Metric") FontWeight.Bold else FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(id = R.string.imperial),
                    style = TextStyle(
                        fontWeight = if (clickedOption == "Imperial") FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
        if (clickedOption == stringResource(id = R.string.metric)) {
            OutlinedTextField(
                value = heightMetric,
                onValueChange = { heightMetric = it },
                label = { Text(text = stringResource(id = R.string.height_bmi)) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconPicker(heightErrorMetric, stringResource(id = R.string.height_bmi_metric))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                supportingText = { ErrorHint(heightErrorMetric) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            OutlinedTextField(
                value = weightMetric,
                onValueChange = { weightMetric = it },
                label = { Text(text = stringResource(id = R.string.weight_bmi)) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconPicker(heightErrorMetric, stringResource(id = R.string.weight_bmi_metric))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                supportingText = { ErrorHint(weightErrorMetric) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        else {
            OutlinedTextField(
                value = heightFeet,
                onValueChange = { heightFeet = it },
                label = { Text(text = stringResource(id = R.string.height_bmi)) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconPicker(heightErrorImp, stringResource(id = R.string.height_bmi_imperial_feet))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                supportingText = { ErrorHint(heightErrorMetric) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            OutlinedTextField(
                value = heightInch,
                onValueChange = { heightInch = it },
                label = { Text(text = stringResource(id = R.string.height_bmi)) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconPicker(heightErrorImp, stringResource(id = R.string.height_bmi_imperial_inch))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                supportingText = { ErrorHint(heightErrorMetric) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = weightImp,
                onValueChange = { weightImp = it },
                label = { Text(text = stringResource(id = R.string.weight_bmi)) },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconPicker(heightErrorMetric, stringResource(id = R.string.weight_bmi_imperial))
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                },
                supportingText = { ErrorHint(weightErrorImp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
            )
        }
        Button(
            onClick = {
                if (clickedOption == "Metric") {
                    weightErrorMetric = (weightMetric == "" || weightMetric == "0")
                    heightErrorMetric = (heightMetric == "" || heightMetric == "0")
                    if(weightErrorMetric || heightErrorMetric) return@Button
                    bmi = hitungBmiMetric(weightMetric.toFloat(), heightMetric.toFloat())
                }
                else if (clickedOption == "Imperial"){
                    weightErrorImp = (weightImp == "" || weightImp == "0")
                    heightErrorImp = (heightFeet == "" || heightFeet == "0" || heightInch == "")
                    if(weightErrorImp || heightErrorImp) return@Button
                    bmi = hitungBmiImperial(weightImp.toFloat(), heightFeet.toFloat(), heightInch.toFloat())
                }

                category = getKategori(bmi)
                action = getAction(bmi)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.calculate))
        }
        if (bmi != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(id = R.string.bmi_res, bmi),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(id = category).uppercase(),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = stringResource(id = action),
                style = MaterialTheme.typography.bodyMedium
            )
            val bmiCategoryText = stringResource(id = getKategori(bmi))
            val bmiActionText = stringResource(id = getAction(bmi))
            val bmiText = stringResource(id = R.string.bmi_res, bmi)

            Button(onClick = {
                Toast.makeText(context, R.string.bmi_saved, Toast.LENGTH_LONG).show()
                viewModel.insert(
                    "bmi",
                    "$bmiText\n$bmiCategoryText\n$bmiActionText",
                    "1")
            },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)) {
                Text(text = stringResource(id = R.string.bmi_save))
            }
        }
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

private fun hitungBmiMetric(weight : Float, height : Float): Float {
    return weight / (height / 100).pow(2)
}

private fun hitungBmiImperial(weight : Float, heightF : Float, heightIn: Float): Float {
    return (weight / ((heightF * 12) + heightIn).pow(2)) * 703
}

private fun getKategori(bmi : Float): Int {
    return when {
        bmi < 18.5 -> R.string.bmi_underweight
        bmi in 18.5..24.9 -> R.string.bmi_norm_weight
        bmi in 25.0..29.9 -> R.string.bmi_ovr_weight
        else -> R.string.bmi_obese_weight
    }
}

private fun getAction(bmi : Float): Int {
    return when {
        bmi < 18.5 -> R.string.bmi_underweight_txt
        bmi in 18.5..24.9 -> R.string.bmi_normalweight_txt
        bmi in 25.0..29.9 -> R.string.bmi_overweight_txt
        else -> R.string.bmi_obese_txt
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    MiniProjectTheme {
        BmiScreen(rememberNavController())
    }
}