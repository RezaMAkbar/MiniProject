package org.d3if3125.miniproject.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.d3if3125.miniproject.R
import org.d3if3125.miniproject.database.NoteDb
import org.d3if3125.miniproject.model.Note
import org.d3if3125.miniproject.navigation.Screen
import org.d3if3125.miniproject.ui.theme.MiniProjectTheme
import org.d3if3125.miniproject.util.SettingsDataStore
import org.d3if3125.miniproject.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(navController: NavHostController) {
    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    val sorts = listOf(
        stringResource(id = R.string.sort_by_title_desc),
        stringResource(id = R.string.sort_by_title_asc),
        stringResource(id = R.string.sort_by_date_desc),
        stringResource(id = R.string.sort_by_date_asc),
        stringResource(id = R.string.sort_by_cate_desc),
        stringResource(id = R.string.sort_by_cate_asc),
        stringResource(id = R.string.sort_by_stat)
    )

    val items = listOf(
        stringResource(id = R.string.home),
        stringResource(id = R.string.konversi_suhu),
        stringResource(id = R.string.konversi_berat),
        stringResource(id = R.string.konversi_panjang),
        stringResource(id = R.string.konversi_kecepatan),
        stringResource(id = R.string.bmi),
        stringResource(id = R.string.about_app)
    )
    val screens = listOf(
        Screen.Home,
        Screen.Temp,
        Screen.Weight,
        Screen.Length,
        Screen.Speed,
        Screen.Bmi,
        Screen.About
    )

    var expandedTopMenu by rememberSaveable { mutableStateOf(false) }
    var expandedSort by rememberSaveable { mutableStateOf(false) }
    var selectedIndexTopMenu by rememberSaveable { mutableIntStateOf(0) }
    var selectedIndexSort by rememberSaveable { mutableIntStateOf(runBlocking { dataStore.getSortPreference() }) }

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
                    Text(text = stringResource(id = R.string.note_main_menu))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_purple),
                    titleContentColor = Color.White
                ),

                actions = {
                    Row(
                        modifier = Modifier.clickable(onClick = { expandedSort = true }),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.sort_by),
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    DropdownMenu(
                        expanded = expandedSort,
                        onDismissRequest = { expandedSort = false }
                    ) {
                        sorts.forEachIndexed { index, item ->
                            val isSelected = index == selectedIndexSort
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedIndexSort = index
                                    CoroutineScope(Dispatchers.IO).launch {
                                        dataStore.saveSortPreference(index)
                                        expandedSort = false
                                    }
                                },
                                modifier = Modifier.background(if (isSelected) Color.LightGray else Color.Transparent)
                            )
                        }
                    }
                    IconButton(onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!showList)
                        }
                    }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = Color.White,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                    Row(
                        modifier = Modifier.clickable(onClick = { expandedTopMenu = true }),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
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
                                        4 -> Icon(
                                            painter = painterResource(R.drawable.speed),
                                            contentDescription = null,
                                            modifier = Modifier.padding(start = 4.dp)
                                        )
                                        5 -> Text(
                                            text = stringResource(R.string.bmi),
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddNote.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_note),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    ) { padding -> ScreenContent(showList, Modifier.padding(padding), navController, selectedIndexSort) }
}


@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController, sort: Int) {
    val context = LocalContext.current
    val db = NoteDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: NoteMainViewModel = viewModel(factory = factory)
    val data by when (sort) {
        0 -> {
            viewModel.titleDesc.collectAsState()
        }
        1 -> {
            viewModel.titleAsc.collectAsState()
        }
        2-> {
            viewModel.dateDesc.collectAsState()
        }
        3 -> {
            viewModel.dateAsc.collectAsState()
        }
        4 -> {
            viewModel.cateDesc.collectAsState()
        }
        5 -> {
            viewModel.cateAsc.collectAsState()
        }
        6 -> {
            viewModel.stat.collectAsState()
        }
        else -> {
            viewModel.titleDesc.collectAsState()
        }
    }


    if (data.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.magnifying_glass),
                contentDescription = "Empty Note",
                modifier = Modifier.size(100.dp)
            )
            Text(text = stringResource(id = R.string.note_empty))
        }
    }
    else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(note = it) {
                        navController.navigate(Screen.EditNote.withId(it.id))
                    }
                    Divider()
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(note = it) {
                        navController.navigate(Screen.EditNote.withId(it.id))
                    }
                }
            }
        }
    }
}

@Composable
fun GridItem(note: Note, onClick: () -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column (
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = note.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note.content,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
            val categoryResourceId = when (note.category) {
                "0" -> R.string.note_type_gen
                "1" -> R.string.note_type_bmi
                "2" -> R.string.note_type_todo
                else -> R.string.note_type_gen
            }
            Text(text = stringResource(id = categoryResourceId))

            if (note.category == "2") {
                val statusResourceId = when (note.status) {
                    "0" -> R.string.todo_status_unfinished
                    "1" -> R.string.todo_status_finished
                    else -> R.string.todo_status_unfinished
                }
                Text(text = stringResource(id = statusResourceId))
            }
            Text(text = note.date)
        }
    }
}

@Composable
fun ListItem(note: Note, onClick: () -> Unit = {}) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = note.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = note.content,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        val categoryResourceId = when (note.category) {
            "0" -> R.string.note_type_gen
            "1" -> R.string.note_type_bmi
            "2" -> R.string.note_type_todo
            else -> R.string.note_type_gen
        }
        Text(text = stringResource(id = categoryResourceId))

        if (note.category == "2") {
            val statusResourceId = when (note.status) {
                "0" -> R.string.todo_status_unfinished
                "1" -> R.string.todo_status_finished
                else -> R.string.todo_status_unfinished
            }
            Text(text = stringResource(id = statusResourceId))
        }
        Text(text = note.date)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun NotePreview() {
    MiniProjectTheme {
        NoteScreen(rememberNavController())
    }
}