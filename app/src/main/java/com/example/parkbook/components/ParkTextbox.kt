package com.example.parkbook.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.parkbook.ui.theme.Purple80
import com.example.parkbook.ui.theme.PurpleGrey80


@Composable
fun ParkTextbox(labelName: String) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(

        value = text,
        onValueChange = { text = it },
        label = { Text(labelName) },
        placeholder = { Text("enter your $labelName") },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (labelName == "Email") {
                KeyboardType.Email
            } else if ( "Password" in labelName ){
                KeyboardType.Password

            } else KeyboardType.Text
        ),
        )
}

@Preview(
    showBackground = true,
//    widthDp = 380,
//    heightDp = 800
)
@Composable
fun ParkTextboxPreview() {
    ParkTextbox("Email")
}