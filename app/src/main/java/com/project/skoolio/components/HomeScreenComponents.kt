package com.project.skoolio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.project.skoolio.navigation.AppScreens


//@Preview
//@Composable
//fun prev()
//{
//    SkoolioAppBar()
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkoolioAppBar(
    pageTitle: String,
    icon: ImageVector? = null,
    elevation: Dp = 0.dp,
    navController: NavHostController
){
    val showDialog = remember {        // show dialog for MoreVert button
        mutableStateOf(false)
    }
    if(showDialog.value == true){
        ShowDropDownMenu(showDialog, navController)
    }

    val context = LocalContext.current

    TopAppBar(
        title = {
            Row {
                Column {
                    Spacer(modifier = Modifier.height(3.dp))
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

                Text(
                    text = pageTitle,
                    color = Color.Black,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.LightGray
        ),
        actions = {
            IconButton(onClick = { showDialog.value = !showDialog.value }) {
                Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = null)
            }
        },
        navigationIcon = {
            IconButton(onClick = { /*Open Side Bar*/ }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
            }           
        }
    )
}

@Composable
fun ShowDropDownMenu(showDialog: MutableState<Boolean> ,
                     navController: NavHostController
) {
    val doSignOut:()->Unit = {
            //Need to update a state that tells if user is logged in or not.
            //Also other data to be removed.
        navController.navigate(AppScreens.LoginScreen.name)
    }
    val optionsTask:(String)->Unit = {option->
        when (option) {
            "Sign Out" -> {
                doSignOut.invoke()
            }

            else -> navController.navigate(AppScreens.LoginScreen.name)
        }
    }

    var expanded by remember {
        mutableStateOf(true)
    }
    val listOfOptions = listOf("Sign Out")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomEnd)
            .absolutePadding(top = 45.dp, right = 20.dp),
    ) {
        DropdownMenu(expanded = expanded, onDismissRequest = {
            expanded = false
            showDialog.value = false
        }, modifier = Modifier
            .width(140.dp)
            .background(Color.White)) {
            listOfOptions.forEachIndexed { index, option ->
                DropdownMenuItem(text = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()) {
                        Icon(imageVector = when(option){
                            "Sign Out"-> Icons.AutoMirrored.Filled.ExitToApp
                            else -> Icons.Default.Settings
                        }
                            , contentDescription = null)
                        Text(text = option,
                            Modifier
                                .fillMaxHeight()
                                .clickable {
                                    showDialog.value = false
                                    expanded = false
                                    optionsTask.invoke(option)
                                }, fontWeight = FontWeight.W300)
                    }
                }, onClick = {
                    showDialog.value = false
                    expanded = false
                })
            }
        }
    }
}
