package com.example.compose_list_me_app

import android.content.Context
import androidx.room.Room
import com.example.compose_list_me_app.comments.data.datasource.CommentRemoteDatasource
import com.example.compose_list_me_app.comments.data.repositories.CommentRepositoryImpl
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository
import com.example.compose_list_me_app.database.ListMeDatabase
import com.example.compose_list_me_app.posts.data.datasource.PostDatasource
import com.example.compose_list_me_app.posts.data.repositories.PostRepositoryImpl
import com.example.compose_list_me_app.posts.domain.repositories.PostRepository
import com.example.compose_list_me_app.todo.data.datasource.TodoRemoteDatasource
import com.example.compose_list_me_app.todo.data.repositories.TodoRepositoryImpl
import com.example.compose_list_me_app.todo.domain.repositories.TodoRepository
import com.example.compose_list_me_app.users.data.datasource.UserRemoteDataSource
import com.example.compose_list_me_app.users.data.respositories.UserRepositoryImpl
import com.example.compose_list_me_app.users.domain.repositories.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userRepository: UserRepository
    val postRepository: PostRepository
    val commentRepository: CommentRepository
    val todoRepository: TodoRepository
}

class DefaultAppContainer(context: Context) : AppContainer {

    companion object {
        private const val BASEURL = "https://jsonplaceholder.typicode.com"
    }

    private val retrofit =
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val db by lazy {
        Room.databaseBuilder(
            context = context, klass = ListMeDatabase::class.java, name = "list_me_database.db"
        ).build()
    }

    private val retrofitUserDatasource: UserRemoteDataSource by lazy {
        retrofit.create(UserRemoteDataSource::class.java)
    }
    private val retrofitPostDatasource: PostDatasource by lazy {
        retrofit.create(PostDatasource::class.java)
    }

    private val retrofitCommentDatasource: CommentRemoteDatasource by lazy {
        retrofit.create(CommentRemoteDatasource::class.java)
    }

    private val retrofitTodoDatasource: TodoRemoteDatasource by lazy {
        retrofit.create(TodoRemoteDatasource::class.java)
    }


    override val userRepository: UserRepository by lazy {
        UserRepositoryImpl(remoteDataSource = retrofitUserDatasource)
    }
    override val postRepository: PostRepository by lazy {
        PostRepositoryImpl(postDatasource = retrofitPostDatasource)
    }
    override val commentRepository: CommentRepository by lazy {
        CommentRepositoryImpl(
            commentRemoteDatasource = retrofitCommentDatasource, db.commentDao
        )
    }
    override val todoRepository: TodoRepository by lazy {
        TodoRepositoryImpl(
            todoRemoteDatasource = retrofitTodoDatasource,
            todoDao = db.todoDao
        )
    }
}