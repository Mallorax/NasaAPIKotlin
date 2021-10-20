package com.example.spaceinformer.ui.details

import android.text.BoringLayout
import androidx.lifecycle.*
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.example.spaceinformer.repository.RepositoryResponse
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel
@Inject constructor(private val repo: IVRepositoryImpl,
                    private val roomRepo: FavouritesRepo): ViewModel() {

    //TODO: For now its questionable if this viewmodel is needed
    // or if other one could be reused for details fragment, reevaluate this later in the project
    private val _detailedIvItem = MutableLiveData<IvItem>()
    val detailedIvItem: LiveData<IvItem> get() = _detailedIvItem

    private val _errorNotification = MutableLiveData<String>()
    val errorNotification: LiveData<String> get() = _errorNotification

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getSpecificIV(nasaId: String){
        viewModelScope.launch(Dispatchers.IO){
            _loading.postValue(true)
            val response = repo.getIVWithNasaId(nasaId)
            if (response.status == RepositoryResponse.Status.SUCCESS){
                val isFavourite = roomRepo.isFavourite(nasaId)
                if (isFavourite){
                    response.data?.data?.first()?.favourite = true
                }
                _detailedIvItem.postValue(response.data!!)
            }else if (response.status == RepositoryResponse.Status.ERROR){
                _errorNotification.postValue(response.message!!)
            }
            _loading.postValue(false)
        }
    }


    fun updateFavourite(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = _detailedIvItem.value
            if (data != null){
                data.data?.first()?.favourite = !data.data?.first()?.favourite!!
                _detailedIvItem.postValue(data!!)
                roomRepo.saveToFavourites(data.data?.first()!!)
            }
        }
    }

}