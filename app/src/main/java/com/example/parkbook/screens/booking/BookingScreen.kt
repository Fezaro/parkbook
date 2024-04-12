package com.example.parkbook.screens.booking

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.parkbook.components.DateTimePickerButton
import com.example.parkbook.data.BookingUIEvent
import com.example.parkbook.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    navController: NavController,
    bookingViewModel: BookingViewModel = viewModel()


) {
    val user = FirebaseAuth.getInstance().currentUser
    var employerSelected by remember { mutableStateOf("") }
    val employers = listOf("USIU-A", "kBIT", "MOTOGP") // Replace with your list of employers
    var employerDropdownExpanded by remember { mutableStateOf(false) }
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedFromTime by remember { mutableStateOf(LocalTime.now()) }
    var pickedToTime by remember { mutableStateOf(LocalTime.now()) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val TAG = "BookingScreen"

    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(listOf<String>()) }

    bookingViewModel.snackbarHostState = snackbarHostState
    bookingViewModel.scope = scope
    bookingViewModel.navController = navController

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val formattedFromTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("HH:mm")
                .format(pickedFromTime)
        }
    }

    val formattedToTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("HH:mm")
                .format(pickedToTime)
        }
    }


    val dateDialogState = rememberMaterialDialogState()
    val timeFromDialogState = rememberMaterialDialogState()
    val timeToDialogState = rememberMaterialDialogState()

//    LaunchedEffect(key1 = Unit) {
//        scope.launch {
//            val db = FirebaseFirestore.getInstance()
//            db.collection("PB_OFFICES").document("MOTOGP")
//                .collection("parking_spots")
//                .get()
//                .addOnSuccessListener { result ->
//                    val fetchedOptions = result.documents.map { it.id }
//                    Log.d(TAG, "Document data: ${fetchedOptions}")
//                    options = fetchedOptions
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
//        }
//    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "ParkBook") },
                    actions = {
                        IconButton(onClick = {
                            user?.let {
                                FirebaseAuth.getInstance().signOut()
                                navController.navigate(Screen.Login.route)
                            }
                        }) {
                            Icon(Icons.Filled.ExitToApp, contentDescription = "Logout")
                        }
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Booking Screen")
                Spacer(modifier = Modifier.height(16.dp))
//                OutlinedTextField(
//                    value = employerSelected,
//                    onValueChange = { employerSelected = it },
//                    label = { Text("Employer") },
//                    readOnly = true,
//                    trailingIcon = {
//                        IconButton(onClick = { employerDropdownExpanded = true }) {
//                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
//                        }
//                    }
//                )

                ExposedDropdownMenuBox(
                    expanded = employerDropdownExpanded,
                    onExpandedChange = {
                        Log.d(TAG, "Employer Dropdown Expanded")
                        employerDropdownExpanded = !employerDropdownExpanded
//
                    }
                ) {
//                    TextField(
//                        modifier = Modifier.menuAnchor(),
//                        value = employerSelected,
//                        onValueChange ={},
//                        readOnly = true,
//                        trailingIcon = {
//                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = employerDropdownExpanded)
//                        }
//                    )
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = employerSelected,
                        onValueChange = {

                        },
                        label = { Text("Office") },
                        readOnly = true,
//                    trailingIcon = {
//                        IconButton(onClick = { employerDropdownExpanded = true }) {
//                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
//                        }
//                    }
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = employerDropdownExpanded)
                        }
                    )

                    ExposedDropdownMenu(

                        expanded = employerDropdownExpanded,
                        onDismissRequest = { employerDropdownExpanded = false }
                    ) {
                        employers.forEachIndexed { index, employer ->
                            DropdownMenuItem(
                                text = { Text(text = employer) },
                                onClick = {
                                    employerDropdownExpanded = false
                                    employerSelected = employers[index]
                                    if (employerSelected.isNotEmpty()) {
                                    bookingViewModel.onEvent(
                                        BookingUIEvent.bookingEmployerChanged(
                                            employerSelected
                                        )
                                    )

                                            scope.launch {
                                                val db = FirebaseFirestore.getInstance()
                                                db.collection("PB_OFFICES")
                                                    .document(employerSelected)
                                                    .collection("parking_spaces")
                                                    .get()
                                                    .addOnSuccessListener { result ->
                                                        val fetchedOptions =
                                                            result.documents.map { it.id }
                                                        Log.d(
                                                            TAG,
                                                            "Document data: ${fetchedOptions}"
                                                        )
                                                        options = fetchedOptions
                                                    }
                                                    .addOnFailureListener { exception ->
                                                        Log.w(
                                                            TAG,
                                                            "Error getting documents: ",
                                                            exception
                                                        )
                                                    }
                                            }
                                        }

                                },
                            )
                        }

                    }
                }
//                DropdownMenu(
//                    expanded = employerDropdownExpanded,
//                    onDismissRequest = { employerDropdownExpanded = false }
//                ) {
//                    employers.forEach { employer ->
//                        DropdownMenuItem(onClick = {
//                            employerDropdownExpanded = false
//                            employerSelected = employer
//                        }) {
//                            Text(text = employer)
//                        }
//                    }
//                }
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(
                    expanded = dropdownExpanded,
                    onExpandedChange = {
                        dropdownExpanded = !dropdownExpanded


                    }
                ) {
                    OutlinedTextField(
                        placeholder = { Text("Select a Parking spot") },
                        modifier = Modifier.menuAnchor(),
                        value = selectedOption,
                        onValueChange = {},
                        label = { Text("Parking Spot") },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                        }

                    )

                    ExposedDropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false },

                    ) {
                        options.forEachIndexed { index, option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    dropdownExpanded = false
                                    selectedOption = option
                                    bookingViewModel.onEvent(
                                        BookingUIEvent.bookingSpotChanged(
                                            selectedOption
                                        )
                                    )




                                }
                            )
                        }
                    }
                }
                Text(text = "Parking Date and Time")
                DateTimePickerButton(
                    text = formattedDate,
                    icon = Icons.Default.DateRange,
                    onButtonClick = {
                        dateDialogState.show()
                    }
                )


                Text(text = "From")
                DateTimePickerButton(
                    text = formattedFromTime,
                    icon = Icons.Default.DateRange,
                    onButtonClick = {
                        timeFromDialogState.show()

                    }
                )

                Text(text = "To")
                DateTimePickerButton(
                    text = formattedToTime,
                    icon = Icons.Default.DateRange,
                    onButtonClick = {
                        timeToDialogState.show()

                    }
                )

                Button(

                    onClick = {
                        bookingViewModel.onEvent(BookingUIEvent.bookingButtonClicked)
                    }) {
                    Text(
                        text = "Book Spot"
                    )

                }


            }
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(
                        text = "Select",
                        onClick = {
                            bookingViewModel.onEvent(
                                BookingUIEvent.bookingDateChanged(
                                    formattedDate
                                )
                            )
                        }

                    )




                    negativeButton(
                        text = "Cancel"
                    )
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",

                    ) {
                    pickedDate = it

                }
            }

            MaterialDialog(
                dialogState = timeFromDialogState,
                buttons = {
                    positiveButton(
                        text = "Select",
                        onClick = {
                            bookingViewModel.onEvent(
                                BookingUIEvent.bookingFromTimeChanged(
                                    formattedFromTime
                                )
                            )
                        }
                    )
                    negativeButton(
                        text = "Cancel"
                    )
                }
            ) {
                timepicker(
                    initialTime = LocalTime.now(),
                    title = "Pick a time",
                    timeRange = LocalTime.of(5, 0)..LocalTime.of(21, 0)

                ) {
                    pickedFromTime = it
                }
            }

            Text(text = "To")

            MaterialDialog(
                dialogState = timeToDialogState,
                buttons = {
                    positiveButton(
                        text = "Select",
                        onClick = {
                            bookingViewModel.onEvent(
                                BookingUIEvent.bookingToTimeChanged(
                                    formattedToTime
                                )
                            )
                        }
                    )
                    negativeButton(
                        text = "Cancel"
                    )
                }
            ) {
                timepicker(
                    initialTime = LocalTime.now(),
                    title = "Pick a time",
                    timeRange = LocalTime.of(5, 0)..LocalTime.of(21, 0)

                ) {
                    pickedToTime = it
                }
            }

        }
    }

}

@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}