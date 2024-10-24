package com.example.compose_list_me_app.comments.data.datasource.localDatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.compose_list_me_app.comments.domain.models.Comment


@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentsOfPost(postId: Int): List<Comment>

    @Insert
    suspend fun addComment(comment: Comment)
}