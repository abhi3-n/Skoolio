package com.project.skoolio.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.skoolio.R
import com.project.skoolio.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(800,
                easing = {
                    OvershootInterpolator(8f).getInterpolation(it)
                }
            ))
        delay(2000L)

        navController.navigate(AppScreens.LoginScreen.name)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Surface(
            Modifier
                .padding(15.dp)
                .size(330.dp)
                .scale(scale.value),
            shape = CircleShape,
            color = Color.White
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Application logo"
            )
        }
    }
}
