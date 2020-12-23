package dev.akat.veka.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.akat.veka.local.model.CommentEntity
import io.reactivex.Observable

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments WHERE comments.post_id = :postId ORDER BY comments.date")
    fun getComments(postId: Int): Observable<List<CommentEntity>>

    @Query("SELECT * FROM comments WHERE comments.post_id = :postId ORDER BY comments.date")
    fun getCommentsByPost(postId: Int): PagingSource<Int, CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<CommentEntity>)
}
