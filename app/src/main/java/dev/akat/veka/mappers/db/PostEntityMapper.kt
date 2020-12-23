package dev.akat.veka.mappers.db

import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.mappers.Mapper
import dev.akat.veka.model.Post

class PostEntityMapper : Mapper<PostEntity, Post> {

    override fun map(from: PostEntity): Post =
        Post(
            id = from.id,
            date = from.date,
            sourceId = from.sourceId,
            sourceName = from.sourceName,
            avatarUrl = from.avatarUrl,
            photoUrl = from.photoUrl,
            content = from.content,
            likes = from.likes,
            comments = from.comments,
            shares = from.shares,
            isFavorite = from.isFavorite
        )
}
