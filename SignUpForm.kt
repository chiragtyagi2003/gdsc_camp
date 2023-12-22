package com.example.buzzz.ui.theme
import androidx.compose.material3.MaterialTheme

// SignUpForm.kt

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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.google.firebase.firestore.FirebaseFirestore
// Import the Log class
import android.util.Log
import androidx.navigation.NavController


private val auth: FirebaseAuth = Firebase.auth


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpForm(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var name by remember { mutableStateOf(TextFieldValue()) }
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
            text = "Sign Up",
            fontSize = 56.sp
            )


        // Name TextField
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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
//                    performSignUp(username.text, password.text, context)
//                }
//            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )


        // Signup Button
        Button(
            onClick = {
                // Handle signup button click
                performSignUp(name.text, username.text, password.text, context, navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Sign Up")
        }

        // back to login button
        Button(onClick = {
                         navController.navigate("login")
                         },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text("Have An Account? ")
        }
    }
}

private fun performSignUp(name: String, username: String, password: String, context: Context, navController: NavController) {
    // Firebase authentication
    auth.createUserWithEmailAndPassword(username, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user: FirebaseUser? = auth.currentUser
                if (user?.email?.endsWith("@mail.jiit.ac.in") == true) {
                    // Sign up success, update UI with the signed-up user's information
                    Toast.makeText(context, "Sign up successful as ${user.email}", Toast.LENGTH_SHORT).show()

                    // Store additional user information in Firestore
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        val uid = currentUser.uid
                        val userMap = mapOf(
                            "uid" to uid,
                            "name" to name,
                            "username" to user.email
                        )

                        val firestore = FirebaseFirestore.getInstance()
                        firestore.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d("Firestore", "User information stored successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.w("Firestore", "Error storing user information", e)
                            }
                    }

                    // Navigate to home screen
                    navController.navigate("home")
                } else {
                    // If the email doesn't match the required domain, sign out the user and show an error message.
                    auth.signOut()
                    Toast.makeText(context, "Invalid email domain. Please use @mail.jiit.ac.in", Toast.LENGTH_SHORT).show()
                }
            } else {
                // If sign up fails, display a message to the user.
                Toast.makeText(context, "Sign up failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
}



@Composable
@Preview
fun SignUpFormPreview() {
    // You can customize the preview parameters, such as the device and theme
    // For example, you can use previewDevice = "Pixel 4" and previewTheme = "light"
    val navController = rememberNavController()
    MaterialTheme {
        Surface {
            SignUpForm(navController)
        }
    }
}