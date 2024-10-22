package com.example.compose_list_me_app.comments.data.repositories

import com.example.compose_list_me_app.comments.data.datasource.CommentRemoteDatasource
import com.example.compose_list_me_app.comments.data.datasource.localDatasource.CommentDao
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository
import kotlinx.coroutines.flow.Flow

class CommentRepositoryImpl(
    private val commentRemoteDatasource: CommentRemoteDatasource,
    private val commentDao: CommentDao
) : CommentRepository {
    override suspend fun fetchCommentsOfPost(postId: Int): List<Comment> {

        var comments = listOf<Comment>()
        try {
            val response = commentRemoteDatasource.getCommentsOfPost(postId)
            if (response.isSuccessful) {
                response.body()?.let {
                    comments = it
                }
            }
            return comments
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addComment(comment: Comment) = commentDao.addComment(comment)

    override fun getCommentsOfPost(postId: Int): Flow<List<Comment>> =
        commentDao.getCommentsOfPost(postId)
}