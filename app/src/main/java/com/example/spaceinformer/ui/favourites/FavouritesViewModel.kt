package com.example.spaceinformer.ui.favourites

import androidx.lifecycle.ViewModel
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.ivrepo.IVRetrofitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val retrofitRepo: IVRetrofitRepository,
    private val roomFavouritesRepo: FavouritesRepo
) : ViewModel() {


}