package dev.akat.veka.utils

import dev.akat.veka.api.model.attachments.AttachmentsDto
import dev.akat.veka.api.model.attachments.PhotoDto
import dev.akat.veka.api.model.attachments.SizeDto
import dev.akat.veka.api.model.posts.CountDto
import dev.akat.veka.api.model.posts.GroupDto
import dev.akat.veka.api.model.posts.LikesDto
import dev.akat.veka.api.model.posts.NewsDto
import dev.akat.veka.api.model.posts.ProfileDto
import kotlin.random.Random

class PostFactory {

    fun createProfileDto() = ProfileDto(
        id = Random.nextInt(0, Int.MAX_VALUE),
        firstName = "Jake",
        lastName = "Wharton",
        screenName = "wharton",
        photo50 = "https://via.placeholder.com/50",
        photo100 = "https://via.placeholder.com/100",
    )

    fun createGroupDto() = GroupDto(
        id = Random.nextInt(0, Int.MAX_VALUE),
        type = "page",
        name = "Square",
        description = "Group description",
        screenName = "android",
        photo50 = "https://via.placeholder.com/50",
        photo100 = "https://via.placeholder.com/100",
        photo200 = "https://via.placeholder.com/200",
    )

    fun createNewsDto(sourceId: Int) = NewsDto(
        id = Random.nextInt(0, Int.MAX_VALUE),
        date = getUnixtime(),
        attachments = listOf(createAttachmentsDto(createSizeDto())),
        sourceId = sourceId,
        text = "News by $sourceId",
        comments = createCountDto(),
        likes = createLikesDto(),
        reposts = createCountDto(),
    )

    fun createSizeDto() = SizeDto(
        height = 1080,
        url = "https://via.placeholder.com/150",
        type = "r",
        width = 1920,
    )

    fun createAttachmentsDto(sizeDto: SizeDto) = AttachmentsDto(
        type = "photo",
        photo = PhotoDto(listOf(sizeDto))
    )

    fun createCountDto() = CountDto(count = 20)

    fun createLikesDto() = LikesDto(
        count = 20,
        userLikes = 1,
        canLike = 1,
        canPublish = 1
    )

    private fun getUnixtime(): Long = System.currentTimeMillis() / 1000L
}
