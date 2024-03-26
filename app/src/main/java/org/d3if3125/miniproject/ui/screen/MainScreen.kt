package org.d3if3125.miniproject.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3125.miniproject.R
import org.d3if3125.miniproject.navigation.Screen
import org.d3if3125.miniproject.ui.theme.MiniProjectTheme

@Composable
fun MainMenuContent(modifier: Modifier, navController: NavController) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.main_menu),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
        )
        Button(
            onClick = { navController.navigate(Screen.Temp.route) },
            modifier = Modifier.padding(top = 25.dp),
            contentPadding = PaddingValues(horizontal = 65.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.konversi_suhu))
        }
        Button(
            onClick = {

            },
            modifier = Modifier.padding(top = 15.dp),
            contentPadding = PaddingValues(horizontal = 82.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.konversi_panjang))
        }
        Button(
            onClick = {

            },
            modifier = Modifier.padding(top = 15.dp),
            contentPadding = PaddingValues(horizontal = 62.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.konversi_berat))
        }
        Button(
            onClick = {

            },
            modifier = Modifier.padding(top = 15.dp),
            contentPadding = PaddingValues(horizontal = 34.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.luas_kel_bangunDatar))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
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
    ) { padding -> MainMenuContent(Modifier.padding(padding), navController) }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainPreview() {
    MiniProjectTheme {
        MainScreen(rememberNavController())
    }
}