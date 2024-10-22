package com.example.compose_list_me_app

import com.example.compose_list_me_app.comments.data.datasource.CommentDatasource
import com.example.compose_list_me_app.posts.data.datasource.PostDatasource
import com.example.compose_list_me_app.comments.data.repositories.CommentRepositoryImpl
import com.example.compose_list_me_app.posts.data.repositories.PostRepositoryImpl
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository
import com.example.compose_list_me_app.posts.domain.repositories.PostRepository
import com.example.compose_list_me_app.users.data.datasource.UserRemoteDataSource
import com.example.compose_list_me_app.users.data.respositories.UserRepositoryImpl
import com.example.compose_list_me_app.users.domain.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val postRepository: PostRepository
    val commentRepository: CommentRepository
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

    private val retrofitUserDatasource: UserRemoteDataSource by lazy {
        retrofit.create(UserRemoteDataSource::class.java)
    }
    private val retrofitPostDatasource: PostDatasource by lazy {
        retrofit.create(PostDatasource::class.java)
    }

    private val retrofitCommentDatasource: CommentDatasource by lazy {
        retrofit.create(CommentDatasource::class.java)
    }


    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(remoteDataSource = retrofitUserDatasource)
    }
    override val postRepository: PostRepository by lazy {
        PostRepositoryImpl(postDatasource = retrofitPostDatasource)
    }
    override val commentRepository: CommentRepository by lazy {
        CommentRepositoryImpl(commentDatasource = retrofitCommentDatasource)
    }
}