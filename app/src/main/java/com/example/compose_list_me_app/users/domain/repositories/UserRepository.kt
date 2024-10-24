package com.example.compose_list_me_app.users.domain.repositories

import com.example.compose_list_me_app.users.domain.models.album.Album
import com.example.compose_list_me_app.users.domain.models.photo.Photo
import com.example.compose_list_me_app.users.domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun fetchAllUsers(): List<User>
    suspend fun fetchUserAlbums(userId: Int): List<Album>
    suspend fun fetchAlbumPhotos(albumId: Int): List<Photo>
}