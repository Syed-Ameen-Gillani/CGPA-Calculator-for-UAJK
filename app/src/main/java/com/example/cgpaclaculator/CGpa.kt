package com.example.cgpaclaculator

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cgpaclaculator.ui.theme.Black
import com.example.cgpaclaculator.ui.theme.Blackgradient
import com.example.cgpaclaculator.ui.theme.BlueGray
import com.example.cgpaclaculator.ui.theme.CGPAclaculatorTheme
import com.example.cgpaclaculator.ui.theme.Whitegradient
import com.example.cgpaclaculator.ui.theme.anotherGradient
import com.example.cgpaclaculator.ui.theme.buttonColor
import com.example.cgpaclaculator.ui.theme.orange

class CGpa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPAclaculatorTheme {
                Screen2(context= this)
            }
        }
    }
}
@Composable
fun Screen2(context:Context) {
    Surface {
        var calculatedCGPA by rememberSaveable { mutableStateOf(0.0,) }
        var totalCreditHours by rememberSaveable { mutableStateOf(0) }
        var totalGrades by rememberSaveable { mutableStateOf("") }
        val uiColor = if (isSystemInDarkTheme()) Color.White else Black
        var openDialog = rememberSaveable {
            mutableStateOf(false)
        }

        var gpa1 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit1 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa2 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit2 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa3 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit3 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa4 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit4 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa5 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit5 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa6 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit6 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa7 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit7 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa8 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit8 = rememberSaveable { mutableStateOf<Int?>(null) }
        var gpa9 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit9 = rememberSaveable { mutableStateOf<Int?>(null) }

        var gpa10 = rememberSaveable { mutableStateOf<Double?>(null) }
        var credit10 = rememberSaveable { mutableStateOf<Int?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) Blackgradient else Whitegradient)
                .verticalScroll(rememberScrollState())
                .padding(0.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calculate CGPA",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = uiColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa1, credit1,"smester 1")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa2, credit2,"smester 2")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa3, credit3,"smester 3")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa4, credit4,"smester 4")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa5, credit5,"smester 5")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa6, credit6,"smester 6")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa7, credit7,"smester 7")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa8, credit8,"smester 8")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa9, credit9,"smester 9")
            Spacer(modifier = Modifier.height(10.dp))
            subjectsection2(gpa10, credit10,"smester 10")
            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(40.dp))
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            calculatedCGPA=calculateCGPA(
                               listOf(gpa1, gpa2, gpa3, gpa4, gpa5, gpa6, gpa7, gpa8, gpa9, gpa10),
                               listOf(credit1, credit2, credit3, credit4, credit5, credit6, credit7, credit8, credit9, credit10)
                           ).first
                            totalCreditHours=calculateCGPA(
                                listOf(gpa1, gpa2, gpa3, gpa4, gpa5, gpa6, gpa7, gpa8, gpa9, gpa10),
                                listOf(credit1, credit2, credit3, credit4, credit5, credit6, credit7, credit8, credit9, credit10)
                            ).second
                            totalGrades= OverAllGrades(calculatedCGPA)
                            openDialog.value = true

                        },
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(32.dp)
                            )
                            .border(
                                4.dp,
                                if (isSystemInDarkTheme()) Whitegradient else anotherGradient,
                                RoundedCornerShape(32.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor
                        )
                    ) {
                        Text(text = "Calculate", color = Color.White)
                    }
                    if (openDialog.value) {
                        AlertDialog(
                            onDismissRequest = { openDialog.value = false },
//                            title = { Text(text = "Developed by Ameen Gillani") },
                            modifier = Modifier.border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)),
                            containerColor = if (isSystemInDarkTheme()) Black else Color.White,
                            textContentColor = uiColor,
                            confirmButton = {
                                    TextField(value = "$calculatedCGPA", onValueChange = {}, enabled = false,
                                        label = { Text(text = "CGPA")},
                                        colors = TextFieldDefaults.colors(
                                            disabledTextColor = uiColor,
                                            disabledLabelColor = uiColor,
                                            disabledContainerColor = Color.Transparent
                                        ))

                                    TextField(value = "$totalCreditHours", onValueChange = {}, enabled = false,
                                        label = { Text(text = "Total Credit Hours")},
                                        colors = TextFieldDefaults.colors(
                                            disabledTextColor = uiColor,
                                            disabledLabelColor = uiColor,
                                            disabledContainerColor =  Color.Transparent
                                        ))
                                    TextField(value = "$totalGrades", onValueChange = {}, enabled = false,
                                        label = { Text(text = "Total Grades")},
                                        colors = TextFieldDefaults.colors(
                                            disabledTextColor = uiColor,
                                            disabledLabelColor = uiColor,
                                            disabledContainerColor =  Color.Transparent
                                        ))
                                    Button(onClick = { openDialog.value = false},
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor),
                                        modifier = Modifier
                                            .clip(
                                                RoundedCornerShape(32.dp)
                                            )
                                            .border(
                                                4.dp,
                                                if (isSystemInDarkTheme()) Whitegradient else anotherGradient,
                                                RoundedCornerShape(32.dp)
                                            )
                                      ) {
                                        Text(text = "Back", color = Color.White)
                                    }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        onClick = {
                            reset(
                                gpa1,
                                credit1,
                                gpa2,
                                credit2,
                                gpa3,
                                credit3,
                                gpa4,
                                credit4,
                                gpa5,
                                credit5,
                                gpa6,
                                credit6,
                                gpa7,
                                credit7,
                                gpa8,
                                credit8,
                                gpa9,
                                credit9,
                                gpa10,
                                credit10,
                            )
                        },
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(32.dp)
                            )
                            .border(
                                4.dp,
                                if (isSystemInDarkTheme()) Whitegradient else anotherGradient,
                                RoundedCornerShape(32.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor
                        )
                    ) {
                        Text(text = "Reset", color = Color.White)
                    }
                    Spacer(modifier = Modifier.padding(4.dp))

                }
            }
        }
    }
}

fun reset(
    gpa1: MutableState<Double?>,
    credit1: MutableState<Int?>,
    gpa2: MutableState<Double?>,
    credit2: MutableState<Int?>,
    gpa3: MutableState<Double?>,
    credit3: MutableState<Int?>,
    gpa4: MutableState<Double?>,
    credit4: MutableState<Int?>,
    gpa5: MutableState<Double?>,
    credit5: MutableState<Int?>,
    gpa6: MutableState<Double?>,
    credit6: MutableState<Int?>,
    gpa7: MutableState<Double?>,
    credit7: MutableState<Int?>,
    gpa8: MutableState<Double?>,
    credit8: MutableState<Int?>,
    gpa9: MutableState<Double?>,
    credit9: MutableState<Int?>,
    gpa10: MutableState<Double?>,
    credit10: MutableState<Int?>
) {
    gpa1.value = null
    credit1.value = null
    gpa2.value = null
    credit2.value = null
    gpa3.value = null
    credit3.value = null
    gpa4.value = null
    credit4.value = null
    gpa5.value = null
    credit5.value = null
    gpa6.value = null
    credit6.value = null
    gpa7.value = null
    credit7.value = null
    gpa8.value = null
    credit8.value = null
    gpa9.value = null
    credit9.value = null
    gpa10.value = null
    credit10.value = null
}

@Composable
fun subjectsection2(gpa: MutableState<Double?>, credit: MutableState<Int?>, text:String) {
    Text(text = text,Modifier.padding(6.dp,0.dp,0.dp,3.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            GPAscreen(gpa)
            Spacer(modifier = Modifier.padding(20.dp))
            DropDownList3(credit, CGPAitems)


        }
    }

}

@Composable
fun GPAscreen(gpa: MutableState<Double?>) {
//    var gpaText by rememberSaveable{ mutableStateOf(gpa.value.toString()) }

    TextField(
        value = if (gpa.value == null ) "" else gpa.value.toString(),
        onValueChange = {
//            gpaText = it
            gpa.value = it.toDoubleOrNull()
        },
        label = { Text(text = "GPA", textAlign = TextAlign.Center) },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
//            .border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)).clip(
//                RoundedCornerShape(32.dp)
//            )
            .width(120.dp)
            .height(50.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
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



fun calculateCGPA(gpAs: List<MutableState<Double?>>, credits: List<MutableState<Int?>>):Pair<Double, Int> {
    val totalCredits = credits.mapNotNull { it.value }.sum()
    val totalWeightedGPA = gpAs.zip(credits) { gpa, credit -> gpa.value?.times((credit.value ?: 0)) ?: 0 }
        .sumByDouble { it.toDouble() }

     if (totalCredits > 0) {
         val total = totalWeightedGPA / totalCredits
         return Pair(total,totalCredits)
     }else
     {
         return Pair(0.0,0)
     }
}


fun OverAllGrades(GPA:Double):String{
   return when(GPA)
    {
       in 4.7..1000.0-> "A+"
       in 4.0..4.7-> "A"
       in 3.5..4.0-> "B+"
       in 3.0..3.5-> "B"
       in 2.0..3.0-> "C"
       in 0.1..2.0-> "F"
       else -> "None"
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList3(credit:MutableState<Int?>, items:List<Int>) {
    var color = if (isSystemInDarkTheme()) Color.White else Black
    var isExpended  = rememberSaveable{
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = isExpended.value,
        onExpandedChange = { isExpended.value = it
        }
    ) {
        TextField(
            keyboardOptions = KeyboardOptions(

            ),
            enabled = false,
            value = if (credit.value == null ) "Credit" else credit.value.toString(),
            onValueChange = {credit.value = it.toIntOrNull()},
            readOnly = true,
            shape = RoundedCornerShape(50.dp),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpended.value)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                disabledTextColor = Color.White,
                disabledTrailingIconColor = Color.White,
                focusedTrailingIconColor = BlueGray,
                disabledContainerColor = if (isSystemInDarkTheme()) BlueGray else orange,
                disabledIndicatorColor = Color.Transparent,
                disabledPlaceholderColor = Color.White
            ),
            modifier = Modifier
                .menuAnchor()
                .width(140.dp)
                .height(50.dp)
//                .border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)).clip(
//                    RoundedCornerShape(32.dp)
//                )

        )
        ExposedDropdownMenu(expanded = isExpended.value, onDismissRequest = {isExpended.value = false},
        ) {
            DropdownMenuItem(
                text = { Text(text = items[0].toString() )},
                onClick = {
                    credit.value = items[0]
                    isExpended.value = false
                })

            DropdownMenuItem(
                text = { Text(text =items[1].toString() )},
                onClick = {
                    credit.value =items[1]
                    isExpended.value = false
                })

            DropdownMenuItem(
                text = { Text(text = items[2].toString() )},
                onClick = {
                    credit.value = items[2]
                    isExpended.value = false
                })

            DropdownMenuItem(
                text = { Text(text = items[3].toString() )},
                onClick = {
                    credit.value = items[3]
                    isExpended.value = false
                })


            DropdownMenuItem(
                text = { Text(text = items[4].toString() )},
                onClick = {
                    credit.value = items[4]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[5].toString() )},
                onClick = {
                    credit.value = items[5]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[6].toString() )},
                onClick = {
                    credit.value = items[6]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[7].toString() )},
                onClick = {
                    credit.value = items[7]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[8].toString() )},
                onClick = {
                    credit.value = items[8]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[9].toString() )},
                onClick = {
                    credit.value = items[9]
                    isExpended.value = false
                })
            DropdownMenuItem(
                text = { Text(text = items[10].toString() )},
                onClick = {
                    credit.value = items[10]
                    isExpended.value = false
                })
        }
    }
}
val CGPAitems = listOf<Int>(12,13,16,17,18,19,20,21,22,23,24)












