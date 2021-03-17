package com.example.spaceinformer.dagger

import androidx.fragment.app.Fragment
import com.example.spaceinformer.application.SpaceInformerApplication
import com.example.spaceinformer.network.PotdService
import com.example.spaceinformer.potd.PotdFragment
import com.example.spaceinformer.repository.PotdRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,
    RepositoryModule::class])
interface ApplicationComponent {
    fun inject(app: SpaceInformerApplication)
    fun retrofitPotdService(): PotdService
    fun potdRepository(): PotdRepository
    fun inject(fragment: PotdFragment)
}