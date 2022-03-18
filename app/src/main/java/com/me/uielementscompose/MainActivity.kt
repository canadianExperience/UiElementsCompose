package com.me.uielementscompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.me.uielementscompose.ui.theme.UiElementsComposeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiElementsComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    MyApp(context = LocalContext.current)
                }
            }
        }
    }
}

@Composable
fun MyApp(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Hi there!")
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Filled.Menu,
                            "Menu"
                        )
                    }
                },
                actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding), context)
    }
}
@Composable
fun MyButton(context: Context) {
    Button(
        modifier = Modifier.padding(end = 16.dp),
        onClick = {
            Toast.makeText(context, "Like button", Toast.LENGTH_SHORT).show()
        },
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text("Like")
    }
}

@Composable
fun ExtendedFab() {
    ExtendedFloatingActionButton(
        onClick = { /* ... */ },
        icon = {
            Icon(
                Icons.Filled.Call,
                contentDescription = "Call"
            )
        },
        text = { Text("Call") }
    )
}

@Composable
fun ScrollingList(context: Context){
    val listSize = 20
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var checkedState by rememberSaveable { mutableStateOf(true) }
    val switchText = if(checkedState) "Scroll to top" else "Scroll to end"

    checkedState = (scrollState.firstVisibleItemIndex == 0)

    Column {
        
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ){
            Switch(
                checked = checkedState,
                onCheckedChange = { switchOn ->
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(
                            if(switchOn) 0 else (listSize - 1)
                        )
                    }

                }
            )
            
            Text(text = switchText)

            IconToggleButton(
                checked = checkedState,
                onCheckedChange = { btnOn ->
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(
                            if(btnOn) 0 else (listSize - 1)
                        )
                    }
                }
            ){
                Icon(
                    imageVector = if (checkedState) Icons.Filled.Star else Icons.Filled.StarBorder,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(
                onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to top")
            }

            Button(
                onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to end")
            }
        }

        LazyColumn(state = scrollState) {
            items(listSize) { item ->
                ImageListItem(context, item)
            }
        }
    }
}

@Composable
fun ImageListItem(context: Context, index: Int) {
    Card(modifier = Modifier
        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable(onClick = {
            Toast
                .makeText(context, "Item number - $index", Toast.LENGTH_SHORT)
                .show()
        })
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.lion),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(10.dp))
           // Text("Item #$index", style = MaterialTheme.typography.subtitle1)
            Column {
                Text(
                    "Alfred Sisley",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                // LocalContentAlpha is defining opacity level of its children
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium,) {
                    Text(
                        "Item #$index",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, context: Context) {
    Column {
        Row(modifier = modifier.padding(16.dp)) {
            MyButton(context)
            ExtendedFab()
        }

        ScrollingList(context)
    }
}

@Preview(
    showBackground = true,
    heightDp = 1200,
    widthDp = 320)
@Composable
fun MyAppPreview() {
    UiElementsComposeTheme {
        MyApp(context = LocalContext.current)
    }
}