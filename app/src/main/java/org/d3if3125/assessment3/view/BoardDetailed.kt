package org.d3if3125.assessment3.view

import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3125.assessment3.R
import org.d3if3125.assessment3.model.Chat
import org.d3if3125.assessment3.model.User
import org.d3if3125.assessment3.navigation.Screen
import org.d3if3125.assessment3.network.ChatApi
import org.d3if3125.assessment3.network.UserDataStore
import org.d3if3125.assessment3.ui.theme.Assessment3Theme

const val BOARD_KEY_ID = "idcontent"
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardDetailed(navController: NavHostController, id: Int? = null){
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    val viewModel: MainViewModel = viewModel()
    val errorMessage by viewModel.errorMessage

    var showChatDialog by remember { mutableStateOf(false) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.board2) + id)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = Color.White,
                ),
                actions = {
                    IconButton(onClick = { viewModel.retrieveData(user.email) }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(id = R.string.refresh),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showChatDialog = true },
                containerColor = Color.Gray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.addBoard),
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        if (id != null) {
            ChatContent(viewModel, user.email, Modifier.padding(padding), navController, id.toString())
        }
        if (showChatDialog) {
            AddChatDialog(
                onDismissRequest = {
                    showChatDialog = false
                },
                onConfirmation = { text, boardId ->
                    viewModel.saveChatData(user.email, user.name, text, boardId)
                    showChatDialog = false
                },
                boardId = id ?: 0
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun DetailedBoardPreview() {
    Assessment3Theme {
        BoardDetailed(rememberNavController())
    }
}

@Composable
fun ChatContent(viewModel: MainViewModel, userId: String, modifier: Modifier, navController: NavHostController, boardId: String) {
    val data by viewModel.chatData
    val status by viewModel.statusChat.collectAsState()

    LaunchedEffect(boardId) {
        viewModel.retrieveChatData(boardId)
    }

    when (status) {
        ChatApi.ChatApiStatus.LOADING -> {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        ChatApi.ChatApiStatus.SUCCESS -> {
            LazyVerticalGrid (
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) { ChatList(chat = it, navController = navController)}
            }
        }

        ChatApi.ChatApiStatus.FAILED -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.error))
                Button(
                    onClick = { viewModel.retrieveData(userId)},
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }
        }
    }
}

@Composable
fun ChatList(chat: Chat, navController: NavHostController) {
    Box (
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray)
            .clickable {
                navController.navigate(Screen.DetailedBoard.route)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(Color(0f, 0f, 0f, 0.5f))
                .padding(4.dp)
        ) {
            Text(
                text = chat.name,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = chat.text,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}