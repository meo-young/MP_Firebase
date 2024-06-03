package com.example.mp_firebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mp_firebase.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ItemViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

//viewModel 생성자를 사용하고 싶은경우
//1. ViewModelProvider사용
//2. Hilt 의존성 주입
class ItemViewModel (private val repository: Repository) : ViewModel(){
    private var _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList = _itemList.asStateFlow()

    fun getAllItems(){
        viewModelScope.launch{
            repository.getAllItems().collect{
                _itemList.value = it
            }
        }
    }

   fun InsertItem(item: Item){
       viewModelScope.launch{
           repository.InsertItem(item)
           getAllItems()
       }
   }

    fun UpdateItem(item: Item){
        viewModelScope.launch{
            repository.UpdateItem(item)
            getAllItems()
        }
    }

    fun DeleteItem(item: Item){
        viewModelScope.launch{
            repository.DeleteItem(item)
            getAllItems()
        }
    }

    fun GetItems(item:Item){
        viewModelScope.launch{
            repository.getItems(item.itemName).collect{
                _itemList.value = it
            }
        }
    }
}