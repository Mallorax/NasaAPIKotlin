package com.example.spaceinformer.application

import android.app.Application
import com.example.spaceinformer.dagger.DaggerApplicationComponent

class SpaceInformerApplication: Application() {
    val appComponent =  DaggerApplicationComponent.create()
}