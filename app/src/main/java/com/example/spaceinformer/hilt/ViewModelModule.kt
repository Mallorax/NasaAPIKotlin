package com.example.spaceinformer.hilt

import com.example.spaceinformer.network.PotdService
import com.example.spaceinformer.repository.PotdRepository
import com.example.spaceinformer.repository.PotdRetrofitRepository
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

     @Inject
     @ViewModelScoped
     fun potdRetrofitRepository(retrofit: PotdService): PotdRetrofitRepository{
         return PotdRetrofitRepository(retrofit)
     }
}