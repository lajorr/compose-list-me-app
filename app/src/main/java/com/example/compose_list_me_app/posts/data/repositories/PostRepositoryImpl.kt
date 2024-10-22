package com.example.compose_list_me_app.posts.data.repositories

import com.example.compose_list_me_app.posts.data.datasource.PostDatasource
import com.example.compose_list_me_app.posts.domain.models.Comment
import com.example.compose_list_me_app.posts.domain.models.Post
import com.example.compose_list_me_app.posts.domain.repositories.PostRepository
import kotlinx.serialization.internal.throwMissingFieldException

class PostRepositoryImpl(private val postDatasource: PostDatasource) : PostRepository {
    override suspend fun fetchAllPosts(): List<Post> {
        try {
            var result: List<Post> = listOf()
            val response = postDatasource.getAllPosts()
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

    override suspend fun fetchUserPosts(userId: Int): List<Post> {

        try {
            var result: List<Post> = listOf()
            val response = postDatasource.getUserPosts(userId)
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