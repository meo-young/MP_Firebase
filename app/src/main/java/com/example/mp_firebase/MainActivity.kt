package com.example.mp_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mp_firebase.screen.InputScreen
import com.example.mp_firebase.screen.ItemList
import com.example.mp_firebase.ui.theme.MP_FirebaseTheme
import com.example.mp_firebase.viewmodel.ItemViewModel
import com.example.mp_firebase.viewmodel.ItemViewModelFactory
import com.example.mp_firebase.viewmodel.Repository
import com.google.firebase.Firebase
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MP_FirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Week12Screen()
                }
            }
        }
    }
}

@Composable
fun Week12Screen(){
    val context = LocalContext.current
    val itemdb = Firebase.database.getReference("ItemDB/items")
    val viewModel : ItemViewModel = viewModel(factory = ItemViewModelFactory(Repository(itemdb)))
    var selectedItem by remember{
        mutableStateOf<Item?>(null)
    }
    val onClick = {item: Item -> selectedItem = item}
    val itemList by viewModel.itemList.collectAsState(initial = emptyList())
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        InputScreen(viewModel = viewModel, selectedItem = selectedItem)
        ItemList(list = itemList, onClick = onClick)
    }
}