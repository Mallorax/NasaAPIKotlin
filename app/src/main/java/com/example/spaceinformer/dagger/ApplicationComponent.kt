package com.example.spaceinformer.dagger

import com.example.spaceinformer.application.SpaceInformerApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(app: SpaceInformerApplication)
}