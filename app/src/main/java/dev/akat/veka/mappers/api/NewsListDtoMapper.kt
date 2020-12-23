package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.attachments.getPhotoUrl
import dev.akat.veka.api.model.posts.NewsDto
import dev.akat.veka.api.model.posts.NewsFeedDto
import dev.akat.veka.api.model.posts.getAvatarUrl
import dev.akat.veka.api.model.posts.getName
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.mappers.Mapper
import java.util.Date

class NewsListDtoMapper : Mapper<NewsFeedDto, List<PostEntity>> {

    override fun map(from: NewsFeedDto): List<PostEntity> =
        from.items
            .map { newsDto ->
                val sourceName = from.groups.getName(newsDto.sourceId)
                    ?: from.profiles.getName(newsDto.sourceId)
                val avatarUrl = from.groups.getAvatarUrl(newsDto.sourceId)
                    ?: from.profiles.getAvatarUrl(newsDto.sourceId)
                postFromDto(newsDto, sourceName, avatarUrl)
            }
            .sortedByDescending { it.date }

    private fun postFromDto(news: NewsDto, sourceName: String?, avatarUrl: String?): PostEntity =
        PostEntity(
            id = news.id,
            date = Date(news.date * DATE_OFFSET),
            sourceId = news.sourceId,
            sourceName = sourceName.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            photoUrl = news.attachments?.getPhotoUrl(PHOTO_SIZE).orEmpty(),
            content = news.text,
            likes = news.likes?.count ?: 0,
            comments = news.comments?.count ?: 0,
            shares = news.reposts?.count ?: 0,
            isFavorite = news.likes?.userLikes == 1,
        )

    private companion object {
        const val PHOTO_SIZE = "r"
        const val DATE_OFFSET = 1000
    }
}
