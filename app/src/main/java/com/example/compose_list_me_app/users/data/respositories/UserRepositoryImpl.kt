package com.example.compose_list_me_app.users.data.respositories

import android.util.Log
import com.example.compose_list_me_app.users.data.datasource.RemoteDataSource
import com.example.compose_list_me_app.users.domain.models.User
import com.example.compose_list_me_app.users.domain.repositories.UserRepository

class UserRepositoryImpl(private val remoteDataSource: RemoteDataSource) : UserRepository {
    override suspend fun fetchAllUsers(): List<User> {
        try {
            var result = listOf<User>()
            val response = remoteDataSource.getAllUsers()
            if (response.isSuccessful) {
                response.body()?.let { userList ->
                    result = userList.map { user ->
                        user.imageUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png"
                        user
                    }
                }
            }
            return result

        } catch (e: Exception) {
            Log.e("UserRepo", "fetchAllUsers: Failed Fetching data")
            throw e
        }
    }
}