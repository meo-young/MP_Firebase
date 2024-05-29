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

    //select : state로 사용해야함
    //state
    //1. flow : 데이터를 가지고 있지 않고 누군가 collect하기 시작하면 데이터를 넘겨줌
    //2. mutable : 데이터를 가지고 있어서 UI를 갱신하는 용도로 사용함
    //3. sharedflow : 데이터 = 라디오 방송 : 데이터는 계속해서 흘러나오고 여러구독자들이 flow데이터를 수신할 수 있음 ex)채팅
    private var _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList = _itemList.asStateFlow()

    fun getAllItems(){
        viewModelScope.launch{
            repository.getAllItems().collect{
                _itemList.value = it
            }
        }
    }

   fun InsertItem(item: Item){ //UI layer에서 호출
       viewModelScope.launch{
           //repository를 이용해 실제로 DB에 추가
           repository.InsertItem(item) //suspend함수는 coroutine scope안에서 실행
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

    /*fun getItems(itemName : String){
        viewModelScope.launch{
            repository.getItems(itemName = itemName).collect{
                _itemList.value = it
            }
        }
    }*/
}