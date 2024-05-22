package com.example.penghuattraction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.penghuattraction.data.Datasource
import com.example.penghuattraction.model.Attraction
import com.example.penghuattraction.ui.theme.PenghuAttractionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PenghuAttractionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AttractionApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "attractionList") {
        composable("attractionList") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.app_name)) },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                    )
                }
            ) { innerPadding ->
                Column(modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
                    AttractionList(
                        attractionList = Datasource().loadAttractions(),
                        onAttractionClick = { attraction ->
                            navController.navigate("attractionDetail/${attraction.id}")
                        }
                    )
                }
        }
        }
        composable("attractionDetail/{attractionId}") { backStackEntry ->
            val attractionId = backStackEntry.arguments?.getString("attractionId")
            val attraction = Datasource().loadAttractions().find { it.id == attractionId?.toInt() }
            attraction?.let {
                AttractionDetailScreen(attraction = it, onBackClick = { navController.navigateUp() })
            }
        }
    }
}

@Composable
fun AttractionCard(modifier: Modifier = Modifier, attraction: Attraction) {
    Card(modifier = modifier.padding(10.dp, 2.dp)) {
        Box(modifier = Modifier) {
            Image(
                painter = painterResource(id = attraction.imageResourceId),
                contentDescription = stringResource(id = attraction.stringResourceId),
                modifier = modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = LocalContext.current.getString(attraction.stringResourceId),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AttractionList(
    attractionList: List<Attraction>,
    onAttractionClick: (Attraction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(attractionList) { attraction ->
            AttractionCard(
                attraction = attraction,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onAttractionClick(attraction) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttractionDetailScreen(attraction: Attraction,onBackClick: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = attraction.stringResourceId)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.backButton)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(innerPadding)
    ) {
        Image(
            painter = painterResource(id = attraction.imageResourceId),
            contentDescription = stringResource(id = attraction.stringResourceId),
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = LocalContext.current.getString(attraction.stringResourceId),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = attraction.descriptionResourceId),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.weight(1f)) // Adjust the weight to move the button to the bottom
        Button(
            onClick = {
                val gmmIntentUri = Uri.parse("geo:0,0?q=${attraction.latitude},${attraction.longitude}(${Uri.encode(context.getString(attraction.stringResourceId))})")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                context.startActivity(mapIntent)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp) // Optional padding for the button
        ) {
            Text(text = stringResource(id = R.string.view_on_map))
        }
    }
}
}

//@Preview
//@Composable
//private fun CardPreview() {
//    PenghuAttractionTheme {
//        val attraction = Attraction(1, R.string.attraction1, R.drawable.jibei, R.string.description1)
//        AttractionDetailScreen(attraction = attraction)
//    }
//}
