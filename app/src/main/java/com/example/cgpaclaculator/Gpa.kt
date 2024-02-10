package com.example.cgpaclaculator

import android.content.Context
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cgpaclaculator.ui.theme.Black
import com.example.cgpaclaculator.ui.theme.Blackgradient
import com.example.cgpaclaculator.ui.theme.BlueGray
import com.example.cgpaclaculator.ui.theme.CGPAclaculatorTheme
import com.example.cgpaclaculator.ui.theme.Whitegradient
import com.example.cgpaclaculator.ui.theme.anotherGradient
import com.example.cgpaclaculator.ui.theme.buttonColor
import com.example.cgpaclaculator.ui.theme.orange
class Gpa : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGPAclaculatorTheme {
                Screen1(context = this)
            }
        }
    }
}
@Composable
fun Screen1(context:Context){
    Surface {
        val uiColor = if (isSystemInDarkTheme()) Color.White else Black
        var openDialog = remember{
            mutableStateOf(false)
        }
        var calculatedCGPA1 by rememberSaveable { mutableStateOf(0.0,) }
        var totalCreditHours1 by rememberSaveable { mutableStateOf(0) }
        var totalGrades1 by rememberSaveable { mutableStateOf("") }


        var grade1 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit1 = rememberSaveable{mutableStateOf<Int?>(null)}

        var grade2 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit2 = remember{ mutableStateOf<Int?>(null)}

        var grade3 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit3 = rememberSaveable{mutableStateOf<Int?>(null)}

        var grade4 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit4 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var grade5 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit5 = rememberSaveable{mutableStateOf<Int?>(null)}

        var grade7 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit7 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var grade8 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit8 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var grade9 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit9 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var grade10 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit10 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var grade6 = rememberSaveable{mutableStateOf<Double?>(null)}
        var credit6 = rememberSaveable{ mutableStateOf<Int?>(null)}

        var allGPA = rememberSaveable{mutableStateOf(0.0)}

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(if (isSystemInDarkTheme()) Blackgradient else Whitegradient)
                .verticalScroll(rememberScrollState()).padding(0.dp,16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Text(
                text = "Calculate GPA",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = uiColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade1,credit1,"Subject 1", items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade2 ,credit2,"Subject 2", items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade3,credit3,"Subject 3", items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade4 ,credit4,"Subject 4", items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade5,credit5,"Subject 5",items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade6 ,credit6,"Subject 6",items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade7 ,credit7,"Subject 7",items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade8 ,credit8,"Subject 8",items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade9 ,credit9,"Subject 9",items)
            Spacer(modifier = Modifier.height(10.dp))
            subjectSection1(grade10 ,credit10,"Subject 10",items)
            Spacer(modifier = Modifier.height(80.dp))
           Column (Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
               Row(horizontalArrangement = Arrangement.Center){
                   Button(onClick = {
                       calculatedCGPA1= calculateCGPA(
                           listOf(grade1, grade2, grade3, grade4, grade5, grade6, grade7, grade8, grade9,grade10),
                           listOf(credit1, credit2, credit3, credit4, credit5, credit6, credit7, credit8, credit9, credit10)
                       ).first
                       totalCreditHours1=calculateCGPA(
                           listOf(grade1, grade2, grade3, grade4, grade5, grade6,grade7,grade8,grade9,grade10),
                           listOf(credit1, credit2, credit3, credit4, credit5, credit6, credit7, credit8, credit9, credit10)
                       ).second
                       totalGrades1 = OverAllGrades(calculatedCGPA1)
                       // Reset individual subject values


                       openDialog.value = true},  modifier = Modifier
                       .clip(
                           RoundedCornerShape(32.dp)).border(4.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)
                       ),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor
                       )) {
                       Text(text = "Calculate",color = Color.White)
                   }
                   if (openDialog.value){
                      AlertDialog(onDismissRequest = { openDialog.value = false },
                          containerColor = if (isSystemInDarkTheme()) Black else Color.White,
                          textContentColor = uiColor,
                          modifier = Modifier.border(3.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)),
                          confirmButton = {
                              TextField(
                                  value = "$calculatedCGPA1",
                                  onValueChange = {},
                                  enabled = false,
                                  label = { Text(text = "Semester GPA") },
                                  colors = TextFieldDefaults.colors(
                                      disabledTextColor = uiColor,
                                      disabledLabelColor = uiColor,
                                      disabledContainerColor = Color.Transparent
                                  )
                              )
                              Spacer(modifier = Modifier.height(4.dp))
                              TextField(
                                  value = "$totalCreditHours1",
                                  onValueChange = {},
                                  enabled = false,
                                  label = { Text(text = "Credit Hours") },
                                  colors = TextFieldDefaults.colors(
                                      disabledTextColor = uiColor,
                                      disabledLabelColor = uiColor,
                                      disabledContainerColor = Color.Transparent
                                  )
                              )
                              Spacer(modifier = Modifier.height(4.dp))
                              TextField(
                                  value = "$totalGrades1",
                                  onValueChange = {},
                                  enabled = false,
                                  label = { Text(text = "Grades") },
                                  colors = TextFieldDefaults.colors(
                                      disabledTextColor = uiColor,
                                      disabledLabelColor = uiColor,
                                      disabledContainerColor = Color.Transparent
                                  )
                              )

                              Button(onClick = {openDialog.value = false
                              },
                                  colors = ButtonDefaults.buttonColors(
                                      containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor),
                                  modifier = Modifier
                                  .clip(
                                      RoundedCornerShape(32.dp)).border(4.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)
                                  )) {

                                  Text(text = "Back", color = Color.White)
                              }

                          })
                   }
                   Spacer(modifier = Modifier.padding(4.dp))
                   Button(onClick = {
                       GPAReset(
                           grade1,
                           credit1,
                           grade2,
                           credit2,
                           grade3,
                           credit3,
                           grade4,
                           credit4,
                           grade5,
                           credit5,
                           grade6,
                           credit6,
                           grade7,
                           credit7,
                           grade8,
                           credit8,
                           grade9,
                           credit9
                           ,grade10,
                           credit10

                       )
                   },  modifier = Modifier
                       .clip(
                           RoundedCornerShape(32.dp)).border(4.dp, if (isSystemInDarkTheme()) Whitegradient else anotherGradient, RoundedCornerShape(32.dp)
                       ),
                       colors = ButtonDefaults.buttonColors(
                           containerColor = if (isSystemInDarkTheme()) BlueGray else buttonColor
                       )) {
                       Text(text = "Reset", color = Color.White)
                   }
                   Spacer(modifier = Modifier.padding(4.dp))

               }
           }
        }
    }
}

private fun GPAReset(
    grade1: MutableState<Double?>,
    credit1: MutableState<Int?>,
    grade2: MutableState<Double?>,
    credit2: MutableState<Int?>,
    grade3: MutableState<Double?>,
    credit3: MutableState<Int?>,
    grade4: MutableState<Double?>,
    credit4: MutableState<Int?>,
    grade5: MutableState<Double?>,
    credit5: MutableState<Int?>,
    grade6: MutableState<Double?>,
    credit6: MutableState<Int?>,
    grade7: MutableState<Double?>,
    credit7: MutableState<Int?>,
    grade8: MutableState<Double?>,
    credit8: MutableState<Int?>,
    grade9: MutableState<Double?>,
    credit9: MutableState<Int?>,
    grade10: MutableState<Double?>,
    credit10: MutableState<Int?>
) {
    grade1.value = null
    credit1.value = null
    grade2.value = null
    credit2.value = null
    grade3.value = null
    credit3.value = null
    grade4.value = null
    credit4.value = null
    grade5.value = null
    credit5.value = null
    grade6.value = null
    credit6.value = null
    grade7.value = null
    credit7.value = null
    grade8.value = null
    credit8.value = null
    grade9.value = null
    credit9.value = null
    grade10.value = null
    credit10.value = null

}

@Composable
fun subjectSection1(
    grade: MutableState<Double?>, credit1: MutableState<Int?>, subject:String,
    items: List<Int>) {
    Text(text = subject,Modifier.padding(6.dp,0.dp,0.dp,3.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)){
            GPAscreen(grade)
            Spacer(modifier = Modifier.padding(20.dp))
            DropDownList2(credit1,items)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList2(credit:MutableState<Int?>, items:List<Int>) {
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
            value = if(credit.value==null) "credit Hours" else credit.value.toString(),
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
//            DropdownMenuItem(
//                text = { Text(text = items[6].toString() )},
//                onClick = {
//                    credit.value = items[6]
//                    isExpended.value = false
//                })
//            DropdownMenuItem(
//                text = { Text(text = items[7].toString() )},
//                onClick = {
//                    credit.value = items[7]
//                    isExpended.value = false
//                })
//            DropdownMenuItem(
//                text = { Text(text = items[8].toString() )},
//                onClick = {
//                    credit.value = items[8]
//                    isExpended.value = false
//                })
//            DropdownMenuItem(
//                text = { Text(text = items[9].toString() )},
//                onClick = {
//                    credit.value = items[9]
//                    isExpended.value = false
//                })
//            DropdownMenuItem(
//                text = { Text(text = items[10].toString() )},
//                onClick = {
//                    credit.value = items[10]
//                    isExpended.value = false
//                })
        }
    }
}


val items = listOf<Int>(1,2,3,4,5,6)






