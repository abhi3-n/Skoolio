package com.project.skoolio.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.navigation.AppScreens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CommonModalNavigationDrawer(drawerState:DrawerState,
                                userType:String?,
                                drawerItemsList: @Composable () -> Unit = {},
                                scaffold: @Composable () -> Unit = {}
                                ) {
    ModalNavigationDrawer(
        gesturesEnabled = true,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .fillMaxHeight(),
            ) {
                ImageSurface(
                    Modifier
                        .padding(4.dp)
                        .height(50.dp)
                        .width(50.dp)
                )
                SideDrawerTitle(userType)
                HorizontalDivider()
                Spacer(modifier = Modifier.height(2.dp))
                drawerItemsList.invoke()
            }
        }
    ) {
        scaffold.invoke()
    }
}


@Composable
fun CommonScaffold(
    title:String,
    navController:NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    mainContent:@Composable (PaddingValues)->Unit = {},
    icon: ImageVector?,
    floatingActionButton: @Composable ()-> Unit = {}
){
    Scaffold(
        topBar = {
            SkoolioAppBar(title,
                navController= navController,
                elevation = 5.dp,
                icon = icon,
                sideDrawerToggle = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        },
        floatingActionButton = floatingActionButton,
    ){
        mainContent.invoke(it)
    }
}


fun adminDrawerItems(navController: NavHostController): @Composable () -> Unit {
    return {
        NavigationDrawerItem(
            label = { Text(text = "Profile Page") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.HomeScreen.name + "/Admin")
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Pending Approvals") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.PendingApprovalsScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Take Attendance") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.TakeAttendanceScreen.name + "/Admin")
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "School Information") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.SchoolInformationScreen.name)
            }
        )

        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Issues") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.IssuesScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Settings") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.SettingsScreen.name + "/Admin")
            }
        )
    }
}

fun teacherDrawerItems(navController: NavHostController): @Composable () -> Unit {
    return {
        NavigationDrawerItem(
            label = { Text(text = "Profile Page") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.HomeScreen.name + "/Teacher")
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Take Attendance") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.TakeAttendanceScreen.name + "/Teacher")
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Issues") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.IssuesScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Settings") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.SettingsScreen.name + "/Teacher")
            }
        )
    }
}

fun studentDrawerItems(navController: NavHostController): @Composable () -> Unit {
    return {
        NavigationDrawerItem(
            label = { Text(text = "Profile Page") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.HomeScreen.name + "/Student")
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Issues") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.IssuesScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Fee Payment") },
            selected = false,
            onClick = {
                navController.navigate(AppScreens.FeePaymentScreen.name)
            }
        )
        Spacer(modifier = Modifier.height(2.dp))
        NavigationDrawerItem(
            label = { Text(text = "Settings") },
            selected = false,
            onClick = {
                    navController.navigate(AppScreens.SettingsScreen.name + "/Student")
            }
        )
    }
}


@Composable
fun ListItem(itemInfo:@Composable ()->Unit = {}, onClick:()->Unit = {}, shape:Shape = CircleShape) {
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .clickable {
                       onClick.invoke()
            },
        shape = shape,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        color = Color(0xFFE6EEFF)
    ) {
        itemInfo.invoke()
    }
}