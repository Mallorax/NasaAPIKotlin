package com.example.spaceinformer.hilt

import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.network.NasaPotdService
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepo
import com.example.spaceinformer.repository.favouritesrepo.FavouritesRepoImpl
import com.example.spaceinformer.repository.ivrepo.IVRepository
import com.example.spaceinformer.repository.ivrepo.IVRetrofitRepository
import com.example.spaceinformer.repository.potdrepo.PotdRepository
import com.example.spaceinformer.repository.potdrepo.PotdRetrofitRepository
import com.example.spaceinformer.room.FavouritesDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
     abstract fun potdRepository(repository: PotdRetrofitRepository): PotdRepository

    @Binds
    abstract fun ivRepository(repository: IVRetrofitRepository): IVRepository

    @Binds
    abstract fun favouritesRepo(repo: FavouritesRepoImpl): FavouritesRepo

     @Inject
     @ViewModelScoped
     fun potdRetrofitRepository(retrofit: NasaPotdService): PotdRepository {
         return PotdRetrofitRepository(retrofit)
     }


    @Inject
    @ViewModelScoped
    fun ivRetrofitRepository(retrofit: NasaIVEndpointService): IVRepository {
        return IVRetrofitRepository(retrofit)
    }

    @Inject
    @ViewModelScoped
    fun favouritesRepository(dao: FavouritesDao): FavouritesRepo{
        return FavouritesRepoImpl(dao)
    }
}