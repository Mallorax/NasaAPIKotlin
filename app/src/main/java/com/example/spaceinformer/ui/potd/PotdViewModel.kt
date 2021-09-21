package com.example.spaceinformer.ui.potd

import androidx.lifecycle.*
import com.example.spaceinformer.nasapi.potd.Potd
import com.example.spaceinformer.repository.potdrepo.PotdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PotdViewModel @Inject constructor(private val repo: PotdRepository): ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val potd = MutableLiveData<Potd>()
    val loading = MutableLiveData<Boolean>()


    fun fetchPotd(){
        viewModelScope.launch {
            loading.value = true
            withContext(Dispatchers.IO){
                val response = repo.getPotd()
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){
                        potd.postValue(response.body())
                        loading.value = false
                    }else{
                        errorMessage.value = "Error: ${response.message()}"
                        loading.value = false
                    }
                }
            }
        }
    }


}