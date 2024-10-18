package com.example.compose_list_me_app.users.data.respositories

import android.util.Log
import com.example.compose_list_me_app.users.data.datasource.UserRemoteDataSource
import com.example.compose_list_me_app.users.domain.models.album.Album
import com.example.compose_list_me_app.users.domain.models.photo.Photo
import com.example.compose_list_me_app.users.domain.models.user.User
import com.example.compose_list_me_app.users.domain.repositories.UserRepository

class UserRepositoryImpl(private val remoteDataSource: UserRemoteDataSource) : UserRepository {
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

    override suspend fun fetchUserAlbums(userId: Int): List<Album> {
        try {
            val response = remoteDataSource.getUserAlbums(userId)
            var result = listOf<Album>()
            if (response.isSuccessful) {
                response.body()?.let {
                    result = it
                }
            }

            return result

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchAlbumPhotos(albumId: Int): List<Photo> {
        try {
            val response = remoteDataSource.getAlbumPhotos(albumId)
            var result = listOf<Photo>()
            if (response.isSuccessful) {
                response.body()?.let {
                    result = it
                }
            }
            return result

        } catch (e: Exception) {
            throw e
        }
    }
}