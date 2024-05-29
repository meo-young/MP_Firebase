package com.example.mp_firebase.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mp_firebase.Item

@Composable
fun ItemList(list: List<Item>, onClick: (Item) -> Unit) {
    LazyColumn {
        items(list) { item ->
            ItemUI(item, onClick)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun ItemUI(item: Item, onClick: (item: Item) -> Unit) {
    Column(modifier = Modifier.padding(10.dp).fillMaxWidth().clickable { onClick(item)}){
        Text(item.itemId.toString(), fontSize = 15.sp)
        Text(item.itemName, fontSize = 15.sp)
        Text(item.itemQuantity.toString(), fontSize = 15.sp)
    }
}