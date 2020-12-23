package dev.akat.veka.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.akat.veka.local.model.NewsEntity
import dev.akat.veka.local.model.PostEntity

@Dao
abstract class NewsDao {

    @Query("SELECT posts.* FROM posts INNER JOIN news ON posts.id = news.post_id ORDER BY posts.date DESC")
    abstract fun getNews(): PagingSource<Int, PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPosts(posts: List<PostEntity>)

    @Query("DELETE FROM news")
    abstract fun deleteAllNews()

    @Query("DELETE FROM news WHERE timestamp < :olderThan")
    abstract fun deleteOldNews(olderThan: Long)

    @Transaction
    open fun addNews(posts: List<PostEntity>) {
        insertPosts(posts)
        insertNews(posts.map { post ->
            NewsEntity(post.id, System.currentTimeMillis())
        })
    }
}
