package com.example.spaceinformer.dagger

import com.example.spaceinformer.network.PotdService
import com.example.spaceinformer.repository.PotdRepository
import com.example.spaceinformer.repository.PotdRetrofitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providePotdRepository(potdService: PotdService): PotdRepository{
        return PotdRetrofitRepository()
    }
}