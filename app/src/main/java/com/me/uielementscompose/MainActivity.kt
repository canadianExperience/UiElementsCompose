package com.me.uielementscompose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UiElementsComposeTheme {
                // A surface container using the 'background' color from the theme
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
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Row() {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                                modifier = Modifier.padding(8.dp)
                            )
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier.padding(8.dp)
                            )
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(Modifier.padding(innerPadding), context)
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier, context: Context) {
    Card(modifier = modifier
        .padding(16.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable(onClick = {
            Toast
                .makeText(context, "Card lion", Toast.LENGTH_SHORT)
                .show()
        })
    ) {
        Row(
            modifier = modifier
                .wrapContentHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
            ) {
                Image(
                    modifier = modifier.fillMaxSize(),
                    painter = painterResource(R.drawable.lion),
                    contentDescription = "background_image",
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = modifier.padding(start = 8.dp)) {
                Text(
                    "Alfred Sisley",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                // LocalContentAlpha is defining opacity level of its children
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium,) {
                    Text(
                        "3 minutes ago",
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.End,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Composable
fun MyButton(modifier: Modifier = Modifier, context: Context) {
    Button(
        modifier = modifier.padding(end = 16.dp),
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
        // Inner content including an icon and a text label
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            modifier = modifier.size(ButtonDefaults.IconSize)
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
fun BodyContent(modifier: Modifier = Modifier, context: Context) {
    Column {
        PhotographerCard(modifier, context)
        Row(modifier = modifier.padding(16.dp)) {
            MyButton(modifier,context)
            ExtendedFab()
        }
    }
}

@Preview(
    showBackground = true,
    heightDp = 1200,
    widthDp = 320
)
@Composable
fun PhotographerCardPreview() {
    UiElementsComposeTheme {
        MyApp(context = LocalContext.current)
    }
}