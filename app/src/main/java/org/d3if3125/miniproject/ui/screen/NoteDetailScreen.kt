package org.d3if3125.miniproject.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3125.miniproject.R
import org.d3if3125.miniproject.database.NoteDb
import org.d3if3125.miniproject.ui.theme.MiniProjectTheme
import org.d3if3125.miniproject.util.ViewModelFactory

const val NOTE_KEY_ID = "idcontent"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = NoteDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: NoteDetailViewModel = viewModel(factory = factory)

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getNote(id) ?: return@LaunchedEffect
        title = data.title
        content = data.content
        category = data.category
        status = data.status
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    if (id == null) {
                        Text(text = stringResource(id = R.string.add_note))
                    } else {
                        Text(text = stringResource(id = R.string.edit_note))
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(id = R.color.light_purple),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        if (title == "") {
                            Toast.makeText(context, R.string.invalid_note, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null) {
                            viewModel.insert(title.lowercase(), content, category)
                        } else {
                            viewModel.update(id, title.lowercase(), content, category, status)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.save),
                            tint = Color.White
                        )
                    }
                    if (id != null) {
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    ) { padding ->
        Formcontent(
            title = title,
            onTitleChange = { title = it },
            content = content,
            onDescChange = { content = it },
            category = category,
            onCategoryChanged = { category = it.toString() },
            status = status,
            onStatusChanged = { status = it.toString() },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteAction(delete: () -> Unit){
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.more_option),
            tint = Color.White
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.delete_note))
                },
                onClick = {
                    expanded = false
                    delete()
                })
        }
    }
}

@Composable
fun Formcontent(
    title: String, onTitleChange: (String) -> Unit,
    content: String, onDescChange: (String) -> Unit,
    category: String, onCategoryChanged: (Int) -> Unit,
    status: String, onStatusChanged: (Int) -> Unit,
    modifier: Modifier
) {

    val noteOptions = listOf(
        stringResource(id = R.string.note_type_gen),
        stringResource(id = R.string.note_type_bmi),
        stringResource(id = R.string.note_type_todo)
    )

    val todoStatus = listOf(
        stringResource(id = R.string.todo_status_unfinished),
        stringResource(id = R.string.todo_status_finished)
    )

    var noteOptionsIndex by remember { mutableStateOf(noteOptions[0]) }
    var todoStatusIndex by remember { mutableStateOf(todoStatus[0]) }

    when (category) {
        "0" -> {
            noteOptionsIndex = noteOptions[0]
        }
        "1" -> {
            noteOptionsIndex = noteOptions[1]
        }
        "2" -> {
            noteOptionsIndex = noteOptions[2]
        }
    }

    when (status) {
        "0" -> {
            todoStatusIndex = todoStatus[0]
        }
        "1" -> {
            todoStatusIndex = todoStatus[1]
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(id = R.string.note_title)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Column (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray)
        ) {
            noteOptions.forEachIndexed { index, text ->
                NoteOptions(
                    label = text,
                    isSelected = noteOptionsIndex == text,
                    modifier = Modifier
                        .selectable(
                            selected = noteOptionsIndex == text,
                            onClick = {
                                noteOptionsIndex = text
                                onCategoryChanged(index)
                            },
                            role = Role.RadioButton
                        )
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            if (category == "2") {
                todoStatus.forEachIndexed { index, text ->
                    NoteStatus(
                        label = text,
                        isSelected = todoStatusIndex == text,
                        modifier = Modifier
                            .selectable(
                                selected = todoStatusIndex == text,
                                onClick = {
                                    todoStatusIndex = text
                                    onStatusChanged(index)
                                },
                                role = Role.RadioButton
                            )
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = content,
            onValueChange = { onDescChange(it) },
            label = {
                Text(text = stringResource(id = R.string.note_content))

            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview() {
    MiniProjectTheme {
        NoteDetailScreen(rememberNavController())
    }
}

@Composable
fun NoteOptions(label : String, isSelected : Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start =  8.dp)
        )
    }
}

@Composable
fun NoteStatus(label : String, isSelected : Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start =  8.dp)
        )
    }
}