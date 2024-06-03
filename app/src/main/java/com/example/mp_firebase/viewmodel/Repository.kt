package com.example.mp_firebase.viewmodel
import com.example.mp_firebase.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class Repository(private val table :DatabaseReference){

    suspend fun InsertItem(item: Item){
        table.child(item.itemId.toString()).setValue(item)
    }

    suspend fun UpdateItem(item: Item){
        table.child(item.itemId.toString()).child(item.itemQuantity.toString()).setValue(item.itemQuantity)
    }

    suspend fun DeleteItem(item: Item){
        table.child(item.itemId.toString()).removeValue()
    }

    fun getItems(itemName:String): Flow<List<Item>> = callbackFlow {
        val query = table.orderByChild("itemName").equalTo(itemName)
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for(itemSnapshot in snapshot.children){
                    val item = itemSnapshot.getValue(Item::class.java)
                    item?.let{
                        itemList.add(item)
                    }
                }
                trySend(itemList) }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        query.addListenerForSingleValueEvent(listener)
        awaitClose{
            query.removeEventListener(listener)
        }
    }
    fun getAllItems(): Flow<List<Item>> = callbackFlow {
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for(itemSnapshot in snapshot.children){
                    val item = itemSnapshot.getValue( Item::class.java)
                    item?.let{
                        itemList.add(it)
                    }
                }
                trySend(itemList)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        table.addValueEventListener(listener)
        awaitClose {
            table.removeEventListener(listener)
        }
    }
}