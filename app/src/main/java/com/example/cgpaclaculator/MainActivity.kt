package com.example.cgpaclaculator

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.cgpaclaculator.ui.theme.Black
import com.example.cgpaclaculator.ui.theme.Blackgradient
import com.example.cgpaclaculator.ui.theme.BlueGray
import com.example.cgpaclaculator.ui.theme.CGPAclaculatorTheme
import com.example.cgpaclaculator.ui.theme.LightBlue
import com.example.cgpaclaculator.ui.theme.Whitegradient
import com.example.cgpaclaculator.ui.theme.anotherGradient
import com.example.cgpaclaculator.ui.theme.orange
import kotlin.jvm.java
import kotlin.jvm.java as java1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPAclaculatorTheme {
                LoginScreen(context = this)
            }
        }
    }
}
@Composable
fun LoginScreen(context: Context)
{
    Surface {
        val uiColor = if (isSystemInDarkTheme()) Color.White else Black
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) Blackgradient else Whitegradient)
        ){
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    val intent = Intent(context,CGpa::class.java1)
                    context.startActivity(intent)
                },
                    modifier = Modifier.border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) BlueGray else orange,
                        contentColor = if (isSystemInDarkTheme()) LightBlue else LightBlue
                    )) {
                    Text(text = "CGPA", style = MaterialTheme.typography.headlineLarge)
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = {
                    val intent = Intent(context,Gpa::class.java1)
                    context.startActivity(intent)
                },
                    modifier = Modifier.border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) BlueGray else orange,
                        contentColor = if (isSystemInDarkTheme()) LightBlue else LightBlue
                    )) {
                    Text(text = "Semester GPA", style = MaterialTheme.typography.headlineLarge, )
                }

                Spacer(modifier = Modifier.padding(10.dp))
                Button(onClick = {
                    val intent = Intent(context,individualsubject::class.java1)
                    context.startActivity(intent)
                },
                    modifier = Modifier.border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) BlueGray else orange,
                        contentColor = if (isSystemInDarkTheme()) LightBlue else LightBlue
                    )) {
                    Text(text = "Subject GPA", style = MaterialTheme.typography.headlineLarge)
                }

            }
            Column(modifier = Modifier.align(Alignment.BottomCenter).padding(0.dp,20.dp)){
                Text(
                    text = "Developed by Syed Ameen Gillani",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }

        }
    }
}






