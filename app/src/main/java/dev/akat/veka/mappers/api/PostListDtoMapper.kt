package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.attachments.getPhotoUrl
import dev.akat.veka.api.model.posts.PostDto
import dev.akat.veka.api.model.posts.WallPostsDto
import dev.akat.veka.api.model.posts.getAvatarUrl
import dev.akat.veka.api.model.posts.getName
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.mappers.Mapper
import java.util.Date

class PostListDtoMapper : Mapper<WallPostsDto, List<PostEntity>> {

    override fun map(from: WallPostsDto): List<PostEntity> =
        from.items
            .map { postDto ->
                val sourceName = from.groups.getName(postDto.ownerId)
                    ?: from.profiles.getName(postDto.ownerId)
                val avatarUrl = from.groups.getAvatarUrl(postDto.ownerId)
                    ?: from.profiles.getAvatarUrl(postDto.ownerId)
                postFromDto(postDto, sourceName, avatarUrl)
            }
            .sortedByDescending { it.date }

    private fun postFromDto(post: PostDto, sourceName: String?, avatarUrl: String?): PostEntity =
        PostEntity(
            id = post.id,
            date = Date(post.date * DATE_OFFSET),
            sourceId = post.ownerId,
            sourceName = sourceName.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            photoUrl = post.attachments?.getPhotoUrl(PHOTO_SIZE).orEmpty(),
            content = post.text,
            likes = post.likes?.count ?: 0,
            comments = post.comments?.count ?: 0,
            shares = post.reposts?.count ?: 0,
            isFavorite = post.likes?.userLikes == 1,
        )

    private companion object {
        const val PHOTO_SIZE = "r"
        const val DATE_OFFSET = 1000
    }
}
