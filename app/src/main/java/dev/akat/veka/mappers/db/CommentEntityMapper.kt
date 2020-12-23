package dev.akat.veka.mappers.db

import dev.akat.veka.local.model.CommentEntity
import dev.akat.veka.mappers.Mapper
import dev.akat.veka.model.Comment

class CommentEntityMapper : Mapper<CommentEntity, Comment> {

    override fun map(from: CommentEntity): Comment =
        Comment(
            id = from.id,
            date = from.date,
            postId = from.postId,
            ownerId = from.ownerId,
            sourceId = from.sourceId,
            sourceName = from.sourceName,
            avatarUrl = from.avatarUrl,
            text = from.text,
            likes = from.likes,
        )
}
