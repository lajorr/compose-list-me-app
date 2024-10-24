package com.example.compose_list_me_app.comments.data.repositories

import com.example.compose_list_me_app.comments.data.datasource.CommentRemoteDatasource
import com.example.compose_list_me_app.comments.data.datasource.localDatasource.CommentDao
import com.example.compose_list_me_app.comments.domain.models.Comment
import com.example.compose_list_me_app.comments.domain.repositories.CommentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CommentRepositoryImpl(
    private val commentRemoteDatasource: CommentRemoteDatasource, private val commentDao: CommentDao
) : CommentRepository {
    override fun fetchCommentsOfPost(postId: Int): Flow<List<Comment>> = flow {
        try {

            val response = commentRemoteDatasource.getCommentsOfPost(postId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun addComment(comment: Comment) = commentDao.addComment(comment)

    override suspend fun getLocalCommentsOfPost(postId: Int): Flow<List<Comment>> = flow {
        try {
            emit(commentDao.getCommentsOfPost(postId))
        } catch (e: Exception) {
            throw e
        }
    }
}