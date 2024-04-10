package com.example.parkbook.components

import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parkbook.ui.theme.BrightOrange
import com.example.parkbook.ui.theme.DarkOrange

@Composable
fun ClickableRedirectText(
    labelText: String,
    linkText: String,
    navRoutes: Map<String, String >,
    navController: NavController
) {
    val text = labelText
    val link = linkText

    val redirectString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(fontSize = 14.sp, color = BrightOrange )
        ) {
            append(text)
        }
        withStyle(
            style = SpanStyle(fontSize = 14.sp, color = DarkOrange)
        ) {
            pushStringAnnotation(
                tag = "URL",
                annotation = link)
            append(link)
        }
    }
    ClickableText(text = redirectString, onClick = {
        offset ->
        Log.d("ClickableRedirectText", "onClick triggered")
        redirectString.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.also {
            Log.d("ClickableRedirectText", "Clicked on: ${it.item}" )
            navRoutes[it.item]?.let { route ->
                Log.d("ClickableRedirectText", "Navigating to: $route")
                navController.navigate(route)
        }
        }
    })


}