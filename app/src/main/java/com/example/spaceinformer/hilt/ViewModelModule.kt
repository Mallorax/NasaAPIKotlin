package com.example.spaceinformer.hilt

import com.example.spaceinformer.network.NasaIVEndpointService
import com.example.spaceinformer.network.NasaPotdService
import com.example.spaceinformer.repository.IVRepository
import com.example.spaceinformer.repository.IVRetrofitRepository
import com.example.spaceinformer.repository.PotdRepository
import com.example.spaceinformer.repository.PotdRetrofitRepository
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

     @Inject
     @ViewModelScoped
     fun potdRetrofitRepository(retrofit: NasaPotdService): PotdRetrofitRepository{
         return PotdRetrofitRepository(retrofit)
     }

    @Binds
    abstract fun ivRepository(repository: IVRetrofitRepository): IVRepository

    @Inject
    @ViewModelScoped
    fun ivRetrofitRepository(retrofit: NasaIVEndpointService): IVRetrofitRepository{
        return IVRetrofitRepository(retrofit)
    }
}