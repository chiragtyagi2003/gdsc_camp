package com.example.buzzz.ui.theme

// LoginForm.kt

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

private val auth: FirebaseAuth = Firebase.auth
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login Screen",
            fontSize = 36.sp
            )

        // Username TextField
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Password TextField
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null)},
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
//            keyboardActions = KeyboardActions(
//                onDone = {
//                    keyboardController?.hide()
//                    // Handle login when Done button is clicked
//                    performLogin(username.text, password.text, context, navController)
//                }
//            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


        // Login Button
        Button(
            onClick = {
                // Handle login button click
                performLogin(username.text, password.text, context, navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ){
            Text("New User?")

            Button(
                onClick = {
                    // Handle login button click
                    navController.navigate("signup")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ){
                Text("Create Account")
            }

    }
}

private fun performLogin(username: String, password: String, context: Context, navController: NavHostController) {
    // Check if the email ends with "@mail.jiit.ac.in"
    if (!username.endsWith("@mail.jiit.ac.in")) {
        Toast.makeText(context, "Invalid email domain. Please use @mail.jiit.ac.in", Toast.LENGTH_SHORT).show()
        return
    }

    // Firebase authentication login
    auth.signInWithEmailAndPassword(username, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user: FirebaseUser? = auth.currentUser
                Toast.makeText(context, "Login successful as ${user?.email}", Toast.LENGTH_SHORT).show()

                // Navigate to home screen
                navController.navigate("home")

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
}



@Composable
@Preview
fun LoginFormPreview() {
    // You can customize the preview parameters, such as the device and theme
    // For example, you can use previewDevice = "Pixel 4" and previewTheme = "light"
    val navController = rememberNavController()
    MaterialTheme {
        Surface {
            LoginForm(navController)
        }
    }
}