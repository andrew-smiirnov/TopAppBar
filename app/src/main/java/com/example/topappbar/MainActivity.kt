package com.example.topappbar

import androidx.navigation.compose.rememberNavController
import com.example.bottomnavigationpanel.button_navigation.BottomNavigation
import com.example.bottomnavigationpanel.button_navigation.NavGraph



import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberScaffoldState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.topappbar.ui.theme.TopAppBarTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopAppBarTheme {
                // A surface container using the 'background' color from the theme
                MainScreen(applicationContext)

            }
        }
    }
}

@SuppressLint( "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context: Context){
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold (
        scaffoldState = scaffoldState,
        snackbarHost = {host ->
            SnackbarHost(hostState = host) {data ->
                Snackbar(
                    backgroundColor = Color.LightGray,
                    snackbarData = data,
                    shape = RoundedCornerShape(16.dp),
                    contentColor = Color.Black,
                    modifier = Modifier.padding(bottom = 50.dp)
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Title")
                },
                backgroundColor = Color.LightGray,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch{
                                scaffoldState.drawerState.open()
                            }


                            Toast.makeText(context, "Menu", Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Item deleted",
                                    actionLabel = "Cancel"
                                )
                                if (result == SnackbarResult.ActionPerformed){
                                    Toast.makeText(context,"Item don't deleted", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete")
                    }
                    IconButton(
                        onClick = {
                            Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share")
                    }
                }
            )
        },
        drawerContent = {
            DrawerHeader()
            DrawerBody()
        },
        bottomBar = {
            BottomNavigation(navController = navController)
        }


    ) {
        NavGraph(navHostController = navController)

    }
}
