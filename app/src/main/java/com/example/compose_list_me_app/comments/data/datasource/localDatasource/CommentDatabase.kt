package com.example.compose_list_me_app.comments.data.datasource.localDatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_list_me_app.comments.domain.models.Comment

@Database(entities = [Comment::class], version = 1, exportSchema = false)
abstract class CommentDatabase : RoomDatabase() {
    abstract val commentDao: CommentDao

}