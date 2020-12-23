package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.comments.CommentDto
import dev.akat.veka.api.model.comments.WallCommentsDto
import dev.akat.veka.api.model.posts.getAvatarUrl
import dev.akat.veka.api.model.posts.getName
import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.mappers.Mapper
import java.util.Date

class CommentDtoMapper : Mapper<WallCommentsDto, List<CommentEntity>> {

    override fun map(from: WallCommentsDto): List<CommentEntity> =
        from.items
            .filter { it.fromId != 0 }
            .map { commentDto ->
                val sourceName = from.groups.getName(commentDto.fromId)
                    ?: from.profiles.getName(commentDto.fromId)
                val avatarUrl = from.groups.getAvatarUrl(commentDto.fromId)
                    ?: from.profiles.getAvatarUrl(commentDto.fromId)
                commentFromDto(commentDto, sourceName, avatarUrl)
            }
            .sortedByDescending { it.date }

    private fun commentFromDto(
        comment: CommentDto,
        sourceName: String?,
        avatarUrl: String?,
    ): CommentEntity =
        CommentEntity(
            id = comment.id,
            date = Date(comment.date * DATE_OFFSET),
            postId = comment.postId,
            ownerId = comment.ownerId,
            sourceId = comment.fromId,
            sourceName = sourceName.orEmpty(),
            avatarUrl = avatarUrl.orEmpty(),
            text = comment.text.orEmpty(),
            likes = comment.likes?.count ?: 0,
        )

    private companion object {
        const val DATE_OFFSET = 1000
    }
}
