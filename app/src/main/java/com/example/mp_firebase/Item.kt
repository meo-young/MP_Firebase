package com.example.mp_firebase

data class Item (
    var itemName:String,
    var itemQuantity:Int,
    var itemId:Int
){
    constructor():this("noInfo",0,0)
}