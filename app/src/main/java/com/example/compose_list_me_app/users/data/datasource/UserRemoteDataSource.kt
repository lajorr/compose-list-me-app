package com.example.compose_list_me_app.users.data.datasource

import com.example.compose_list_me_app.users.domain.models.album.Album
import com.example.compose_list_me_app.users.domain.models.photo.Photo
import com.example.compose_list_me_app.users.domain.models.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRemoteDataSource {
    @GET("/users")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("/users/{userId}/albums")
    suspend fun getUserAlbums(@Path("userId") userId: Int): Response<List<Album>>

    @GET("/albums/{albumId}/photos")
    suspend fun getAlbumPhotos(@Path("albumId") albumId: Int): Response<List<Photo>>
}