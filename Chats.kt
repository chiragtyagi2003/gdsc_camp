package com.example.buzzz.ui.theme

// PhotoListScreen.kt

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.buzzz.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val auth: FirebaseAuth = Firebase.auth

data class Person(val id: Int, val name: String, val photoRes: Int, val username: String)
//
//val peopleList = listOf(
//    Person(1, "John Doe", R.drawable.splash1, username),
//    Person(2, "Jack Ryan", R.drawable.splash5, username),
//    // Add more people as needed
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Users",
                    fontSize = 35.sp,
                    color = Color.White, // Set the text color if needed
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth() // Ensure the TopAppBar fills the width of the screen
            .statusBarsPadding() // Apply status bar padding
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.primary) // Apply navigation bar padding
    )
}


@Composable
fun PhotoListScreen(navController: NavHostController) {
    // State to hold the list of users
    val (users, setUsers) = remember { mutableStateOf<List<Person>>(emptyList()) }

    // Fetch data from Firestore
    LaunchedEffect(Unit) {
        fetchUsersFromFirestore { fetchedUsers ->
            setUsers(fetchedUsers)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            Button(
                onClick = {
                          navController.navigate("groupChat")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                ) {
                Text("Enter Group Chat")
                
            }
        }
    ) {
        LazyColumn(contentPadding = it) {
            items(users) { person ->
                PhotoListItem(person = person)
            }
        }
    }


}



//@Composable
fun fetchUsersFromFirestore(onUsersFetched: (List<Person>) -> Unit) {
    // Use the Firebase Firestore SDK to query your "users" collection
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("users")
        .get()
        .addOnSuccessListener { result ->
            val usersList = result.documents.map { document ->
                val userId = document.id
                val name = document.getString("name") ?: ""
                val username = document.getString("username") ?: ""

                // You may need to replace "R.drawable.splash1" with the actual photo resource ID from the document
                Person(userId.hashCode(), name, R.drawable.splash1, username)
            }
            onUsersFetched(usersList)
        }
        .addOnFailureListener { exception ->
            // Handle errors here
            Log.w("Firestore", "Error getting documents.", exception)
        }
}

@Composable
fun PhotoListItem(person: Person) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Photo
            Image(
                painter = painterResource(id = person.photoRes),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .scale(1.2f),
                contentScale = ContentScale.Crop
            )

            // Spacer
            Spacer(modifier = Modifier.width(16.dp))

            // Name and Username
            Column (
                modifier = Modifier.padding(10.dp)
            ){
                Text(
                    text = "Name: ${person.name}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
//                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "Username: ${person.username}",
                    style = MaterialTheme.typography.bodySmall,
//                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}


@Composable
fun ChatScreen(){
    // Input Section with Text Field and Send Button
    var messageText by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    {
        // Display the list of messages above the input section
        MessageList()


        // Add some blank space at the bottom
        Spacer(modifier = Modifier.height(56.dp)) // Adjust the height as needed

        // Input Section with Text Field and Send Button
        var messageText by remember { mutableStateOf("") }
        var isButtonEnabled by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomCenter),
        ) {
            TextField(
                value = messageText,
                onValueChange = {
                    messageText = it
                    isButtonEnabled = it.isNotBlank()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (isButtonEnabled) {
                            onSendMessage(messageText)
                            messageText = ""
                        }
                    }
                ),
                placeholder = {
                    Text("Type a message...")
                }
            )

            Button(
                onClick = {
                    if (isButtonEnabled) {
                        onSendMessage(messageText)
                        messageText = ""
                    }
                },

                enabled = isButtonEnabled,
                modifier = Modifier.height(60.dp)
            ) {
                Text("Send")
            }
        }
    }
}

fun onSendMessage(messageText: String) {
    val firestore = FirebaseFirestore.getInstance()
    val auth: FirebaseAuth = Firebase.auth
    val currentUser = auth.currentUser
    val currentUserId = currentUser?.uid // Adjust this based on your user data model

    // Ensure the current user is not null
    currentUserId?.let {
        val timestamp = System.currentTimeMillis()

        // First, fetch user data from the "users" collection
        firestore.collection("users")
            .document(it)
            .get()
            .addOnSuccessListener { userSnapshot ->
                if (userSnapshot.exists()) {
                    // User data found, extract name and username
                    val name = userSnapshot.getString("name")
                    val username = userSnapshot.getString("username")

                    // Create a map with the message details including name and username
                    val messageData = mapOf(
                        "userId" to it,
                        "messageText" to messageText,
                        "timestamp" to timestamp,
                        "name" to name,
                        "username" to username
                    )

                    // Save the message data to the "groupChat" collection
                    firestore.collection("groupChat")
                        .add(messageData)
                        .addOnSuccessListener {
                            // Message sent successfully
                        }
                        .addOnFailureListener { e ->
                            // Handle the failure
                        }
                } else {
                    // User data not found
                    // Handle the case where user data is not available
                }
            }
            .addOnFailureListener { e ->
                // Handle the failure of fetching user data
            }
    }
}




//@Composable
//fun MessageList() {
//    // State to hold the list of messages
//    val (messages, setMessages) = remember { mutableStateOf<List<Message>>(emptyList()) }
//
//    // Fetch messages from Firestore and update the state
//    LaunchedEffect(Unit) {
//        fetchMessagesFromFirestore { fetchedMessages ->
//            setMessages(fetchedMessages)
//        }
//    }
//
//    // Display the list of messages
//    LazyColumn {
//        items(messages) { message ->
//            MessageCard(message = message)
//        }
//    }
//}


@Composable
fun MessageList() {
    // State to hold the list of messages
    val (messages, setMessages) = remember { mutableStateOf<List<Message>>(emptyList()) }

    // Listen for updates from Firestore
    LaunchedEffect(Unit) {
        val listenerRegistration = listenForMessagesFromFirestore { fetchedMessages ->
            setMessages(fetchedMessages)
        }

//        // Remove the listener when the Composable is disposed
//        onDispose {
//            listenerRegistration.remove()
//        }
    }

    // Display the list of messages
    LazyColumn {
        items(messages) { message ->
            MessageCard(message = message)
        }
    }



}


data class Message(
    val userId: String,
    val messageText: String,
    val timestamp: Long,
    val name: String,
    val username: String,
)


@Composable
fun MessageCard(message: Message) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Row {
                Text(text = message.name)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = message.username)
            }
            // Display message text
            Text(text = message.messageText)

            // Display timestamp converted to date time
            val dateTime = SimpleDateFormat("dd MMM, yyyy HH:mm", Locale.getDefault())
                .format(Date(message.timestamp))
            Text(text = dateTime, color = Color.Gray)
        }
    }
}


// Function to fetch messages from Firestore
//fun fetchMessagesFromFirestore(onSuccess: (List<Message>) -> Unit) {
//    val firestore = FirebaseFirestore.getInstance()
//
//    // Assuming "groupChat" is the collection name
//    firestore.collection("groupChat")
//        .orderBy("timestamp", Query.Direction.ASCENDING) // Order by timestamp in descending order
//        .get()
//        .addOnSuccessListener { documents ->
//            val messageList = mutableListOf<Message>()
//            for (document in documents) {
//                val userId = document.getString("userId") ?: ""
//                val messageText = document.getString("messageText") ?: ""
//                val timestamp = document.getLong("timestamp") ?: 0
//                val name = document.getString("name") ?: ""
//                val username = document.getString("username") ?: ""
//
//                val message = Message(userId, messageText, timestamp, name, username)
//                messageList.add(message)
//            }
//
//            onSuccess(messageList)
//        }
//        .addOnFailureListener { e ->
//            // Handle the failure to fetch messages
//            e.printStackTrace()
//        }
//}

fun listenForMessagesFromFirestore(onUpdate: (List<Message>) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()

    // Assuming "groupChat" is the collection name
    firestore.collection("groupChat")
        .orderBy("timestamp", Query.Direction.ASCENDING)
        .addSnapshotListener { snapshot, e ->
            if (e != null) {
                // Handle the error
                e.printStackTrace()
                return@addSnapshotListener
            }

            val messageList = mutableListOf<Message>()
            for (document in snapshot?.documents ?: emptyList()) {
                val userId = document.getString("userId") ?: ""
                val messageText = document.getString("messageText") ?: ""
                val timestamp = document.getLong("timestamp") ?: 0
                val name = document.getString("name") ?: ""
                val username = document.getString("username") ?: ""

                val message = Message(userId, messageText, timestamp, name, username)
                messageList.add(message)
            }

            onUpdate(messageList)
        }
}


@Preview
@Composable
fun PhotoListScreenPreview() {
    val navController = rememberNavController()
//    PhotoListScreen(navController = navController)
    ChatScreen()
}


