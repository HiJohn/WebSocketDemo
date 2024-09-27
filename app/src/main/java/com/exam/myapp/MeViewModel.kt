package com.exam.myapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeViewModel :ViewModel() {



    fun loadSpinner(onResult:(l:ArrayList<String>)->Unit){
        viewModelScope.launch (Dispatchers.IO){
            val result = arrayListOf<String>()
            result.add("barry")
            result.add("cowell")
            result.add("balcony")
            result.add("valentina")
            result.add("sneak")
            withContext(Dispatchers.Main){

                onResult(result)

            }

        }
    }

}