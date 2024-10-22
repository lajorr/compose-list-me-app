package com.example.compose_list_me_app

import android.app.Application

class ListMeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer(applicationContext)

    }
}