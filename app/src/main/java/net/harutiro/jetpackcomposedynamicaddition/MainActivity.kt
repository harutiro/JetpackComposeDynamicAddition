package net.harutiro.jetpackcomposedynamicaddition

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.harutiro.jetpackcomposedynamicaddition.ui.theme.JetpackComposeDynamicAdditionTheme

data class Hello (
    var index : String,
    var str :String
        )

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDynamicAdditionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    var names = mutableStateListOf<Hello>()
                    var count = 0

                    Column() {
                        Button(
                            onClick = {
                                names.add(Hello(count.toString(),""))
                                count++
                            }
                        ) {
                            Text("追加")
                        }

                        Button(
                            onClick = {
                                for(i in names){
                                    Log.d("test",i.index)
                                    Log.d("test",i.str)

                                }
                            }
                        ) {
                            Text("呼び出し")
                        }

                        AdditionalReadableItems(
                            names = names,
                            get = { name , str , index ->
                                names -= name
                                names.add(index ,Hello(index.toString(),str))
                            },
                            deleted = { name ->
                                names -= name
                            }
                        )
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun AdditionalReadableItems(names: List<Hello>,get:(Hello,String,Int) -> Unit ,deleted:(Hello) -> Unit) {

    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->

            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    var text by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = text,
                        onValueChange = {
                            text = it
                            get(name,it,name.index.toInt())
                                        },
                        label = { Text(text = "名前") },
                        placeholder = { Text(text = "名前を入力してください") },
                        singleLine = true,
                    )

                }

                Button(
                    onClick = {
                        deleted(name)
                    }
                ){
                    Text("delete")
                }
            }
        }
    }
}
