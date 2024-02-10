package com.example.cgpaclaculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cgpaclaculator.ui.theme.Black
import com.example.cgpaclaculator.ui.theme.Blackgradient
import com.example.cgpaclaculator.ui.theme.BlueGray
import com.example.cgpaclaculator.ui.theme.CGPAclaculatorTheme
import com.example.cgpaclaculator.ui.theme.Whitegradient
import com.example.cgpaclaculator.ui.theme.anotherGradient
import com.example.cgpaclaculator.ui.theme.buttonColor
import com.example.cgpaclaculator.ui.theme.orange

class individualsubject : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPAclaculatorTheme {
                individualSubject()
            }
        }
    }
}
@Preview
@Composable
fun individualSubject()
{
   Surface {
       var Marks = rememberSaveable { mutableStateOf<Int?>(null) }
       var Credit = rememberSaveable { mutableStateOf<Int?>(null) }
       var TotalMarks = rememberSaveable { mutableStateOf(0) }
       var Gpa = rememberSaveable { mutableStateOf(0.0) }
       var percentage = rememberSaveable { mutableStateOf(0.0) }
       val uiColor = if (isSystemInDarkTheme()) Color.White else Black
       Column(
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center,
           modifier = Modifier
               .fillMaxSize()
               .background(if (isSystemInDarkTheme()) Blackgradient else Whitegradient)
       ){
           SubjectSection3(Marks, Credit , items)
           Spacer(modifier = Modifier.height(30.dp))
           Row {
               Button(onClick = {
                    TotalMarks.value = getTotalMarks(Credit)
                   percentage.value = Percentage(Marks,TotalMarks)
                   Gpa.value= GpaCalculator(percentage)
               }, colors = ButtonDefaults.buttonColors(
                   containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor.copy(alpha = .9f),
                   contentColor = Color.White
               ),
                   modifier = Modifier
                       .clip(
                           RoundedCornerShape(32.dp)
                       )
                       .border(
                           4.dp,
                           if (isSystemInDarkTheme()) Whitegradient else anotherGradient,
                           RoundedCornerShape(32.dp)
                       )) {
                   Text(text = "Calculate")
               }
               Spacer(modifier = Modifier.padding(4.dp))
               Button(onClick = {
                   reset(Marks, Credit, TotalMarks, Gpa, percentage)
               }, colors = ButtonDefaults.buttonColors(
                   containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor.copy(alpha = .9f),
                   contentColor = Color.White
               ),
                   modifier = Modifier
                       .clip(
                           RoundedCornerShape(32.dp)
                       )
                       .border(
                           4.dp,
                           if (isSystemInDarkTheme()) Whitegradient else anotherGradient,
                           RoundedCornerShape(32.dp)
                       )) {
                   Text(text = "Reset")
               }
           }
           Spacer(modifier = Modifier.height(30.dp))
           Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
               TextFields(label = "percentage", parameter = percentage)
               Spacer(modifier = Modifier.padding(20.dp))
               TextFields(label = "GPA", parameter = Gpa)
           }


       }
   }
}

@Composable
fun SubjectSection3(
    Marks: MutableState<Int?>,
    Credit: MutableState<Int?>,
    item: List<Int>,
)
{
    var uiColor = if(isSystemInDarkTheme()) Color.White else Black

    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Column(horizontalAlignment = Alignment.CenterHorizontally){
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            MarksScreen(Marks)
            Spacer(modifier = Modifier.padding(20.dp))
            DropDownList2(Credit, item)
        }


    }
}


@Composable
fun MarksScreen(Marks: MutableState<Int?>) {

    TextField(
        value = if (Marks.value == null) "" else Marks.value.toString(),
        onValueChange = {
            Marks.value = it.toIntOrNull()
        },
        label = { Text(text = "Marks", textAlign = TextAlign.Center) },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .width(120.dp)
            .height(50.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        readOnly = false,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = if (isSystemInDarkTheme()) BlueGray else orange,
            focusedContainerColor = if (isSystemInDarkTheme()) BlueGray else orange,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            unfocusedLabelColor = Color.White,
            focusedLabelColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledTrailingIconColor = Color.White,
            focusedTrailingIconColor = BlueGray,
            unfocusedTrailingIconColor = Color.White,
            cursorColor = Color.White
        )
    )
}

fun Percentage(Marks: MutableState<Int?>, TotalMarks:MutableState<Int>):Double {
    if(TotalMarks.value == 0 || Marks.value == null)
    {
        return 0.0
    }
    else
    {
        val total = (Marks.value!!.toDouble()/TotalMarks.value)*100
        if(total>100)
        {
            return 0.0
        }
        return total.toDouble()
    }

}


fun GpaCalculator(percentage:MutableState<Double>):Double {
    return when (percentage.value) {
        in 50.0..51.5 -> 2.0
        in 51.5..53.0 -> 2.1
        in 53.0..54.5 -> 2.2
        in 54.5..56.0 -> 2.3
        in 56.0..57.5 -> 2.4
        in 57.5..59.0 -> 2.5
        in 59.0..60.5 -> 2.6
        in 60.5..62.0 -> 2.7
        in 62.0..63.5 -> 2.8
        in 63.5..65.0 -> 2.9
        in 65.0..65.5 -> 3.0
        in 65.5..68.0 -> 3.1
        in 68.0..69.5 -> 3.2
        in 69.5..71.0 -> 3.3
        in 71.0..72.5 -> 3.4
        in 72.5..74.0 -> 3.5
        in 74.0..75.5 -> 3.6
        in 75.5..77.0 -> 3.7
        in 77.0..78.5 -> 3.8
        in 78.5..79.9 -> 3.9
        in 80.0..100.0 -> 4.0
        else -> return 0.0
    }
}




fun getTotalMarks(Credit: MutableState<Int?>):Int {
    return when (Credit.value) {
        1->50
        2->100
        3->150
        4->200
        5->250
        6->300
        else->0
    }
}

@Composable
fun TextFields(label:String, parameter:MutableState<Double>)
{
    TextField(
        keyboardOptions = KeyboardOptions(

        ),
        enabled = false,
        label = {
            Text(text = label, color = Color.White)
        },
        value = parameter.value.toString(),
        onValueChange = {},
        readOnly = true,
        shape = RoundedCornerShape(50.dp),
        trailingIcon = {
        },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.White,
            disabledTrailingIconColor = Color.White,
            focusedTrailingIconColor = BlueGray,
            disabledContainerColor = if (isSystemInDarkTheme()) BlueGray else orange,
            disabledIndicatorColor = Color.Transparent,
            disabledPlaceholderColor = Color.White
        ), modifier = Modifier
            .width(140.dp)
            .height(50.dp)
    )
}


fun  reset(
    Marks: MutableState<Int?>,
    Credit: MutableState<Int?>,
    TotalMarks: MutableState<Int>,
    Gpa: MutableState<Double>,
    percentage: MutableState<Double>
)
{
    Marks.value = null
    Credit.value = null
    TotalMarks.value = 0
    Gpa.value = 0.0
    percentage.value = 0.0
}
