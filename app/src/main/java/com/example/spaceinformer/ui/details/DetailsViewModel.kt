package com.example.spaceinformer.ui.details

import androidx.lifecycle.*
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject constructor(private val repo: IVRepositoryImpl): ViewModel() {

    private val _detailedIvItem = MutableLiveData<DomainIvItem>()
    val detailedAppIvItem: LiveData<DomainIvItem> get() = _detailedIvItem

    private val _errorNotification = MutableLiveData<String>()
    val errorNotification: LiveData<String> get() = _errorNotification

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getSpecificIV(nasaId: String){
        viewModelScope.launch(Dispatchers.IO){
            _loading.postValue(true)
            val response = repo.getIVWithNasaId(nasaId)
            if (response.status == RepositoryResponse.Status.SUCCESS){
                val isFavourite = repo.isFavourite(nasaId)
                if (isFavourite.status == RepositoryResponse.Status.SUCCESS){
                    if (isFavourite.data!!){
                        response.data?.favourite = true
                    }
                }
                _detailedIvItem.postValue(response.data!!)
            }else if (response.status == RepositoryResponse.Status.ERROR){
                _errorNotification.postValue(response.message!!)
            }
            _loading.postValue(false)
        }
    }

    fun toggleFavourite(){
        if (_detailedIvItem.value != null){
            _detailedIvItem.value?.favourite = !_detailedIvItem.value?.favourite!!
            _detailedIvItem.value = _detailedIvItem.value
        }
    }


    fun updateFavourite(){
        if (detailedAppIvItem.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.saveToFavourites(detailedAppIvItem.value!!)
            }
        }
    }

}