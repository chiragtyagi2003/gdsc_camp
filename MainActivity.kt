package com.example.buzzz

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.buzzz.ui.theme.AppNavigation
import com.example.buzzz.ui.theme.BuzzzTheme
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            BuzzzTheme {

                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun SplashScreen1(navController: NavHostController, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.splash1)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color here
    ) {

        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "Buzz",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 126.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(22.dp)
            )

            Text(
                text = "  Find People you Vibe With!",
                color = Color.White,
                fontSize = 25.sp,
                modifier = Modifier.padding(2.dp)
            )
        }

        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.BottomCenter) // Fill the entire screen width
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = {
                    // Handle button click
                  navController.navigate("splashScreen2")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)


            ) {
                Text(
                    text = "Get Started Now!",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
//                    color = Color.Black
                )
            }
        }
    }
}

// splash screen 2
@Composable
fun SplashScreen2(navController: NavHostController, modifier: Modifier = Modifier){
    val image = painterResource(R.drawable.splash2)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color here
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .align(alignment = Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column (
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "Join Buzz",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 55.sp,
                    color = White,
                )

                Text(
                    text = "Today!",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 55.sp,
                    color = White,

                    )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "A GenZ Social!",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 35.sp,
                    color = White,

                    )


            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // Handle button click
                    navController.navigate("splashScreen3")
                },
                modifier = Modifier
                    .padding(8.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Find your Vibe!",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
                    )
            }
        }
    }
}

// splash screen 3
@Composable
fun SplashScreen3(navController: NavHostController, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.splash3)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black) // Set the background color here
    ) {

            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .align(alignment = Alignment.TopStart)

            )


        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column (
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "READY",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 84.sp,
                    color = White,
                    textAlign = TextAlign.Left


                )

                Text(
                    text = "SET",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 84.sp,
                    color = White,
                )

                Text(
                    text = "VIBE",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 84.sp,
                    color = White,

                    )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    // Handle button click
                    navController.navigate("login")

                },
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "Login",
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.W500,
                    fontSize = 24.sp,
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController() // Provide a mock NavHostController
    BuzzzTheme {
        AppNavigation(navController = navController)
//        SplashScreen2(navController = navController)
    }
}
