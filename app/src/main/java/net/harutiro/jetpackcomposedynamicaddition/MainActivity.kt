package net.harutiro.jetpackcomposedynamicaddition

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
    @OptIn(ExperimentalFoundationApi::class)
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intent = Intent(this,MainActivity4::class.java)
//        startActivity(intent)

        setContent {
            JetpackComposeDynamicAdditionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val names = remember { mutableStateListOf<Hello>() }

                    var count = 0

                    Column() {
                        Button(
                            onClick = {
                                if(names.size > 0){
                                    names.add(names.size - 1,Hello(count.toString(),""))
                                }else {
                                    names.add(Hello(count.toString(),""))

                                }
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
//
//                        AdditionalReadableItems(
//                            names = names,
//                            get = { name , str , index ->
//                                names[index] = Hello(index.toString(),str)
//                            },
//                            deleted = { name ->
//                                names.remove(name)
//                            }
//                        )

                        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {

                            itemsIndexed(
                                items = names,
                                key = {index ,i ->
                                    i.index
                                }

                            ) { index , name ->

                                AnimatedVisibility(
                                    modifier = Modifier.animateItemPlacement(),
                                    visible = names.contains(name),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    Row() {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ){

                                            var text by remember { mutableStateOf("") }

                                            OutlinedTextField(
                                                value = text,
                                                onValueChange = {
                                                    text = it
                                                    names[index] = Hello(name.index , it)
                                                },
                                                label = { Text(text = "名前") },
                                                placeholder = { Text(text = "名前を入力してください") },
                                                singleLine = true,
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                names.remove(name)
                                            }
                                        ){
                                            Text("delete")
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}



