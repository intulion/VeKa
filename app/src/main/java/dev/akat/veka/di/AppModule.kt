package dev.akat.veka.di

import android.content.Context
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.akat.veka.VeKaApp
import dev.akat.veka.api.ApiService
import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.api.NetworkDataSourceImpl
import dev.akat.veka.data.CommentRepository
import dev.akat.veka.data.PostRepository
import dev.akat.veka.data.ProfileRepository
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.local.LocalDataSourceImpl
import dev.akat.veka.local.db.AppDatabase
import dev.akat.veka.mappers.api.CommentDtoMapper
import dev.akat.veka.mappers.api.NewsListDtoMapper
import dev.akat.veka.mappers.api.PostListDtoMapper
import dev.akat.veka.mappers.api.UserDtoMapper
import dev.akat.veka.mappers.db.CommentEntityMapper
import dev.akat.veka.mappers.db.PostEntityMapper
import dev.akat.veka.mappers.db.ProfileEntityMapper
import javax.inject.Singleton

@Module
class AppModule(private val application: VeKaApp) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideMovieRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
        newsListDtoMapper: NewsListDtoMapper,
        postListDtoMapper: PostListDtoMapper,
        postEntityMapper: PostEntityMapper,
        pagingConfig: PagingConfig,
    ): PostRepository =
        PostRepository(networkDataSource,
            localDataSource,
            newsListDtoMapper,
            postListDtoMapper,
            postEntityMapper,
            pagingConfig)

    @Provides
    @Singleton
    fun provideProfileRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
        userDtoMapper: UserDtoMapper,
        profileEntityMapper: ProfileEntityMapper,
    ): ProfileRepository =
        ProfileRepository(networkDataSource, localDataSource, userDtoMapper, profileEntityMapper)

    @Provides
    @Singleton
    fun provideCommentRepository(
        networkDataSource: NetworkDataSource,
        localDataSource: LocalDataSource,
        commentDtoMapper: CommentDtoMapper,
        commentEntityMapper: CommentEntityMapper,
        pagingConfig: PagingConfig,
    ): CommentRepository =
        CommentRepository(networkDataSource,
            localDataSource,
            commentDtoMapper,
            commentEntityMapper,
            pagingConfig)

    @Provides
    @Singleton
    fun provideLocalDataSource(database: AppDatabase): LocalDataSource =
        LocalDataSourceImpl(database)

    @Provides
    @Singleton
    fun provideNetworkDataSource(apiService: ApiService): NetworkDataSource =
        NetworkDataSourceImpl(apiService)

    @Provides
    @Singleton
    fun providePagingConfig(): PagingConfig = PagingConfig(
        pageSize = 30,
        enablePlaceholders = false
    )

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "vk.db")
            .fallbackToDestructiveMigration()
            .build()
}
