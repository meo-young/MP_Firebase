package com.example.mp_firebase.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.mp_firebase.Item
import com.example.mp_firebase.viewmodel.ItemViewModel


@Composable
fun InputScreen(viewModel: ItemViewModel, selectedItem: Item?=null) {

    var itemId by remember {
        mutableStateOf("")
    }

    var itemName by remember {
        mutableStateOf("")
    }
    var itemQuantity by remember {
        mutableStateOf("")
    }

    val quantity = itemQuantity.toIntOrNull() ?: 0
    val id = itemId.toIntOrNull() ?: 0
    val item = Item(itemName, quantity, id)

    LaunchedEffect(selectedItem) {
        if (selectedItem != null) {
            itemName = selectedItem.itemName
            itemQuantity = selectedItem.itemQuantity.toString()
            itemId = selectedItem.itemId.toString()
        } 
    }

    fun clearText(){
        itemName = ""
        itemQuantity = ""
        itemId = ""
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = itemId,
            onValueChange = { itemId = it},
            label = { Text("Item ID")},
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Item Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(onClick = {
                viewModel.InsertItem(item)
                clearText()
            }) {
                Text("Insert")
            }
            Button(onClick = {
                viewModel.UpdateItem(item)
                clearText()
            }) {
                Text("Update")
            }
            Button(onClick = {
                viewModel.DeleteItem(item)
                clearText()
            }) {
                Text("delete")
            }
            Button(onClick = {
                viewModel.(item)
                clearText()
            } ) {
                Text("find")
            }
        }
    }
}
