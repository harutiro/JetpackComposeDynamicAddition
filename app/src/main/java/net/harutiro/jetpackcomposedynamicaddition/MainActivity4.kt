package net.harutiro.jetpackcomposedynamicaddition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.harutiro.jetpackcomposedynamicaddition.ui.theme.JetpackComposeDynamicAdditionTheme

class MainActivity4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDynamicAdditionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListAnimation()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListAnimation() {
    // List of String to show in LazyColumn
    val names = remember { mutableStateListOf<String>() }

    Column {
        // Buttons controls to
        // add,remove,shuffle elements
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Button to Remove random element
            Button(
                onClick = {
                    // random number between 0<=x<names.size
                    if( names.size > 0 ){
                        val random = (names.indices).random()
                        names.remove(names[random])
                    }

                    // remove from random index
                }) {
                Text(text = "Remove")
            }

            // Button to add a random string
            Button(onClick = {
                // generate a random string
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val randomString =(1..10)
                    .map { allowedChars.random() }
                    .joinToString("")

                // add random string to the list
                if(names.size > 0){
                    names.add(names.size-1,randomString)
                }else{
                    names.add(names.size,randomString)

                }
            }) {
                Text(text = "Add")
            }

            // Button to shuffle the list
            Button(onClick = {
                // Shuffle
                names.shuffled()
            }) {
                Text(text = "Shuffle")
            }
        }

        // LazyColumn
        LazyColumn {
            // show elements using items function
            items(
                // list of items to display
                items = names,
                key = { it }
            ) { name ->

                AnimatedVisibility(
                    modifier = Modifier.animateItemPlacement(),
                    visible = names.contains(name),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    // Text Composable to display item
                    Text(
                        text = name,

                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .animateItemPlacement()
                        // Important: add this modifier.
                        // Compose will animate items placement
                        // according to the key
                    )
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    JetpackComposeDynamicAdditionTheme {
        ListAnimation()
    }
}