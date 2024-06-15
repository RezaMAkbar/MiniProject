package org.d3if3125.assessment3.view

import android.content.ContentResolver
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CustomCredential
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.canhub.cropper.CropImageView
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import org.d3if3125.assessment3.BuildConfig
import org.d3if3125.assessment3.R
import org.d3if3125.assessment3.model.Board
import org.d3if3125.assessment3.model.User
import org.d3if3125.assessment3.navigation.Screen
import org.d3if3125.assessment3.network.BoardApi
import org.d3if3125.assessment3.network.UserDataStore
import org.d3if3125.assessment3.ui.theme.Assessment3Theme


@Composable
fun Content (viewModel: MainViewModel, userId: String, modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    val data by viewModel.data
    val status by viewModel.status.collectAsState()

    LaunchedEffect(userId) {
        viewModel.retrieveData(userId)
    }

    when (status) {
        BoardApi.ApiStatus.LOADING -> {
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        BoardApi.ApiStatus.SUCCESS -> {
            LazyVerticalGrid (
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) { BoardList(board = it, currentUser = user.name, navController = navController)}
            }
        }

        BoardApi.ApiStatus.FAILED -> {
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
fun BoardList(board: Board, currentUser: String, navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    val viewModel: MainViewModel = viewModel()
    val showDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray)
            .clickable {
                navController.navigate(Screen.DetailedBoard.withId(board.id))
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = board.boardName,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0f, 0f, 0f, 0.5f))
                    .padding(4.dp)
            )
            Text(
                text = board.text,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0f, 0f, 0f, 0.5f))
                    .padding(4.dp)
            )
            Text(
                text = stringResource(id = R.string.createdBy) + " " + board.createdBy,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0f, 0f, 0f, 0.5f))
                    .padding(4.dp)
            )
            if (board.createdBy == currentUser) {
                Text(
                    text = "You created this...",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Yellow,
                    modifier = Modifier
                        .background(Color(0f, 0f, 0f, 0.5f))
                        .padding(4.dp)
                )
                IconButton(
                    onClick = { showDialog.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = Color.White
                    )
                }
            }
        }
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = stringResource(id = R.string.confirm) + board.id) },
                text = { Text(text = stringResource(id = R.string.confirm_text)) },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            viewModel.deleteBoardData(user.email, board.id)
                        }
                    ) {
                        Text(text = stringResource(id = R.string.delete))
                    } },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text(text = stringResource(id = R.string.cancel)) }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDashBoard(navController: NavHostController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(User())
    val viewModel: MainViewModel = viewModel()
    val errorMessage by viewModel.errorMessage
    var showBoardDialog by remember { mutableStateOf(false) }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = R.string.main_board))
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
                    IconButton(onClick = {
                        if (user.email.isEmpty()) {
                            CoroutineScope(Dispatchers.IO).launch { signIn(context, dataStore) }
                        }
                        else {
                            showDialog = true
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.account_circle_24),
                            contentDescription = stringResource(id = R.string.profile),
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showBoardDialog = true },
                containerColor = Color.Gray
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.addBoard),
                    tint = Color.White
                )
            }
        }
    ) {
        padding -> Content(viewModel, user.email, Modifier.padding(padding), navController)
        if (showDialog) {
            ProfileView(
                user = user,
                onDismissRequest = { showDialog = false }) {
                CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStore)}
                showDialog = false
            }
        }
        if (showBoardDialog) {
            BoardDialog(
                onDismissRequest = { showBoardDialog = false }) { boardName, createdBy, text ->
                viewModel.saveData(user.email, boardName, user.name, text)
                showBoardDialog = false
            }
        }
        if(errorMessage != null) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.clearMessage()
        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun MainDashBoardPreview() {
    Assessment3Theme {
        MainDashBoard(rememberNavController())
    }
}

private suspend fun signIn(context: Context, dataStore: UserDataStore){
    val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setServerClientId(BuildConfig.API_KEY)
        .build()

    val request: androidx.credentials.GetCredentialRequest = androidx.credentials.GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    try {
        val credentialManager = androidx.credentials.CredentialManager.create(context)
        val result = credentialManager.getCredential(context, request)
        handleSignIn(result, dataStore)
    } catch (e: androidx.credentials.exceptions.GetCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}

private suspend fun handleSignIn(
    result: androidx.credentials.GetCredentialResponse,
    dataStore: UserDataStore){
    val credential = result.credential
    if (credential is CustomCredential
        && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        try {
            val googlId = GoogleIdTokenCredential.createFrom(credential.data)
            val name = googlId.displayName ?: ""
            val email = googlId.id
            val photoUrl = googlId.profilePictureUri.toString()
            dataStore.saveData(User(name, email, photoUrl))

        } catch (e: GoogleIdTokenParsingException) {
            Log.e("SIGN-IN", "Error: ${e.message}")
        }
    }
    else {
        Log.e("SIGN-IN", "Error unrecognized custom credential type")
    }
}

private suspend fun signOut(context: Context, dataStore: UserDataStore) {
    try {
        val credentialManager = androidx.credentials.CredentialManager.create(context)
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
        dataStore.saveData(User())
    } catch (e: androidx.credentials.exceptions.ClearCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}

fun getCroppedImage(resolver: ContentResolver, result: CropImageView.CropResult): Bitmap? {
    if (!result.isSuccessful) {
        Log.e("IMAGE", "Error: ${result.error}")
        return null
    }

    val uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val souce = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(souce)
    }
}
