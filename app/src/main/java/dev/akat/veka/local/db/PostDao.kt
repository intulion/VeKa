package dev.akat.veka.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.akat.veka.local.model.PostEntity
import io.reactivex.Observable

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY posts.date DESC")
    fun getPosts(): PagingSource<Int, PostEntity>

    @Query("SELECT * FROM posts WHERE posts.is_favorite ORDER BY posts.date DESC")
    fun getFavoritePosts(): PagingSource<Int, PostEntity>

    @Query("SELECT COUNT(posts.id) FROM posts WHERE posts.is_favorite")
    fun getFavoritesCount(): Observable<Int>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getById(id: Int): Observable<PostEntity>

    @Query("SELECT * FROM posts WHERE source_id = :id ORDER BY posts.date DESC")
    fun getBySourceId(id: Int): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPosts(posts: List<PostEntity>)

    @Query("UPDATE posts SET is_favorite = :isLike WHERE id = :id")
    fun setLike(id: Int, isLike: Boolean)

    @Query("DELETE from posts WHERE id = :id")
    fun deletePost(id: Int)
}
