package net.harutiro.jetpackcomposedynamicaddition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.harutiro.jetpackcomposedynamicaddition.ui.theme.JetpackComposeDynamicAdditionTheme
import java.util.*

class MainActivity3 : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeDynamicAdditionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalAnimationApi
@Composable
fun MainContent(){
    Box(
        Modifier
            .background(Color(0xFFEDEAE0))
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val uuids = remember { mutableStateListOf<String>() }

        LazyColumn(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(uuids) { index, item ->
                AnimatedVisibility(
                    visible = uuids.contains(item),
                    enter = expandVertically(),
                    exit = shrinkVertically(animationSpec = tween(durationMillis = 10))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(
                                align = Alignment.CenterVertically
                            ),
//                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = item.take(5).capitalize(),
                                modifier = Modifier.padding(
                                    start = 12.dp, top = 12.dp,
                                    end = 12.dp, bottom = 4.dp
                                ),
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            TextButton(
                                onClick = { uuids.remove(item) },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Remove")
                            }
                        }
                    }
                }

            }
        }

        FloatingActionButton(
            onClick = {
                UUID.randomUUID().toString().apply {
                    uuids.add(this)
                }
            },
            Modifier.align(Alignment.BottomEnd),
        ) { Icon(Icons.Filled.Add,"") }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    JetpackComposeDynamicAdditionTheme {
        MainContent()
    }
}