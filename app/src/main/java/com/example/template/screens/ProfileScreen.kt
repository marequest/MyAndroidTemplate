package com.example.template.screens

import SimpleTopAppBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.template.screens.elements.MyHeaderText
import com.example.template.screens.elements.PasswordField
import com.example.template.screens.elements.SaveButton
import com.example.template.screens.elements.UserProfileField
import com.example.template.screens.elements.UserSignatureField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = { SimpleTopAppBar(scrollBehavior = scrollBehavior, text = "Postavke Naloga") }
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
            .padding(innerPadding)
        ) {
            ProfileInfo()

            PasswordChangeForm()

            DeleteProfile()
        }
    }
}

@Composable
fun ProfileInfo() {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val emailAddress = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }
    val license = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        UserProfileField(label = "Ime", value = firstName)
        UserProfileField(label = "Prezime", value = lastName)
        UserProfileField(label = "Email Adresa", value = emailAddress)
        UserProfileField(label = "Broj telefona", value = phoneNumber)
        UserProfileField(label = "Licenca", value = license)

        UserSignatureField()

        Spacer(modifier = Modifier.height(16.dp))
        SaveButton()
    }
}

@Composable
fun PasswordChangeForm() {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isOldPasswordVisible by remember { mutableStateOf(false) }
    var isNewPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }


    Column(modifier = Modifier.padding(16.dp)) {
        MyHeaderText(text = "Promena Lozinke")
        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            label = "Stara Lozinka",
            password = oldPassword,
            onPasswordChange = { oldPassword = it },
            isPasswordVisible = isOldPasswordVisible,
            onVisibilityChange = { isOldPasswordVisible = !isOldPasswordVisible }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            label = "Nova Lozinka",
            password = newPassword,
            onPasswordChange = { newPassword = it },
            isPasswordVisible = isNewPasswordVisible,
            onVisibilityChange = { isNewPasswordVisible = !isNewPasswordVisible }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            label = "Ponovo Nova Lozinka",
            password = confirmPassword,
            onPasswordChange = { confirmPassword = it },
            isPasswordVisible = isConfirmPasswordVisible,
            onVisibilityChange = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                // Add your save action here
            },
            border = BorderStroke(1.dp, Color.Red),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Promeni Lozinku")
        }
    }
}

@Composable
fun DeleteProfile() {

    Column(modifier = Modifier.padding(16.dp)) {
        MyHeaderText(text = "Uklanjanje Naloga")
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Nakon kompletiranja ove akcije, Vas nalog ce biti uklonjen sa sistema, " +
                "Svi podaci o dnevnicima ce biti sacuvani na nasem sistemu. Nije moguc " +
                "povratak naloga, sistem ce zahtevati ponovno otvaranje naloga.")
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Add your save action here
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ukloni Nalog")
        }
    }

}