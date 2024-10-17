package com.example.compose_list_me_app

import android.app.Application
import com.example.compose_list_me_app.users.data.AppContainer
import com.example.compose_list_me_app.users.data.DefaultAppContainer

class ListMeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()

        container = DefaultAppContainer()

    }
}