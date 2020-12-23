package dev.akat.veka.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.akat.veka.mappers.UriFileMapper
import dev.akat.veka.mappers.api.CommentDtoMapper
import dev.akat.veka.mappers.api.NewsListDtoMapper
import dev.akat.veka.mappers.api.PostListDtoMapper
import dev.akat.veka.mappers.api.UserDtoMapper
import dev.akat.veka.mappers.db.CommentEntityMapper
import dev.akat.veka.mappers.db.PostEntityMapper
import dev.akat.veka.mappers.db.ProfileEntityMapper
import javax.inject.Singleton

@Module
class MappersModule {

    @Provides
    @Singleton
    fun providePostEntityMapper(): PostEntityMapper = PostEntityMapper()

    @Provides
    @Singleton
    fun provideProfileEntityMapper(): ProfileEntityMapper = ProfileEntityMapper()

    @Provides
    @Singleton
    fun provideCommentEntityMapper(): CommentEntityMapper = CommentEntityMapper()

    @Provides
    @Singleton
    fun provideNewsListDtoMapper(): NewsListDtoMapper = NewsListDtoMapper()

    @Provides
    @Singleton
    fun providePostListDtoMapper(): PostListDtoMapper = PostListDtoMapper()

    @Provides
    @Singleton
    fun provideUserDtoMapper(): UserDtoMapper = UserDtoMapper()

    @Provides
    @Singleton
    fun provideCommentDtoMapper(): CommentDtoMapper = CommentDtoMapper()

    @Provides
    @Singleton
    fun provideUriFileMapper(context: Context): UriFileMapper = UriFileMapper(context)
}
