package com.example.spaceinformer.hilt

import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.network.NasaPotdService
import com.example.spaceinformer.repository.ivrepo.IVRepository
import com.example.spaceinformer.repository.ivrepo.IVRepositoryImpl
import com.example.spaceinformer.repository.potdrepo.PotdRepository
import com.example.spaceinformer.repository.potdrepo.PotdRetrofitRepository
import com.example.spaceinformer.room.FavouritesDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

    @Binds
     abstract fun potdRepository(repository: PotdRetrofitRepository): PotdRepository

    @Binds
    abstract fun ivRepository(repositoryImpl: IVRepositoryImpl): IVRepository


     @Inject
     @Singleton
     @ViewModelScoped
     fun potdRetrofitRepository(retrofit: NasaPotdService): PotdRepository {
         return PotdRetrofitRepository(retrofit)
     }


    @Inject
    @Singleton
    @ViewModelScoped
    fun ivRetrofitRepository(retrofit: NasaIVEndpointService, favouritesDao: FavouritesDao): IVRepository {
        return IVRepositoryImpl(retrofit, favouritesDao)
    }

}