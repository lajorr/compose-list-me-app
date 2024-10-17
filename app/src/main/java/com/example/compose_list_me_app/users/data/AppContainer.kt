package com.example.compose_list_me_app.users.data

import com.example.compose_list_me_app.users.data.datasource.RemoteDataSource
import com.example.compose_list_me_app.users.data.respositories.UserRepositoryImpl
import com.example.compose_list_me_app.users.domain.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
}

class DefaultAppContainer : AppContainer {

    companion object {
        private const val baseUrl = "https://jsonplaceholder.typicode.com/"
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val retrofitDatasource: RemoteDataSource by lazy {
        retrofit.create(RemoteDataSource::class.java)
    }


    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(remoteDataSource = retrofitDatasource)
    }
}