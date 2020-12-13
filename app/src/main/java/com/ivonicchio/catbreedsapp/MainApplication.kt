package com.ivonicchio.catbreedsapp

import android.app.Application
import com.ivonicchio.catbreedsapp.di.CatBreedsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(CatBreedsModule)
        }
    }
}
