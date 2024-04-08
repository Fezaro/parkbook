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
import com.example.parkbook.ui.theme.BrightOrange
import com.example.parkbook.ui.theme.DarkOrange

@Composable
fun ClickableRedirectText(
    labelText: String,
    linkText: String,
    onClick: () -> Unit
) {
    var text = labelText
    var link = linkText

    var redirectString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(fontSize = 14.sp, color = BrightOrange )
        ) {
            append(text)
        }
        withStyle(
            style = SpanStyle(fontSize = 14.sp, color = DarkOrange)
        ) {
            pushStringAnnotation(
                tag = "URL: $",
                annotation = link)
            append(link)
        }
    }
    ClickableText(text = redirectString, onClick = {
        offset -> redirectString.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.also {
            Log.d("ClickableRedirectText", "Clicked on: ${it.item}" )
        }
    })


}