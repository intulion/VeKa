package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.posts.CountDto
import dev.akat.veka.api.model.posts.GroupDto
import dev.akat.veka.api.model.posts.LikesDto
import dev.akat.veka.api.model.posts.NewsDto
import dev.akat.veka.api.model.posts.NewsFeedDto
import dev.akat.veka.api.model.posts.ProfileDto
import dev.akat.veka.local.model.PostEntity
import dev.akat.veka.utils.PostFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsListDtoMapperTest {

    private val postFactory = PostFactory()

    private lateinit var photoUrl: String

    private lateinit var dtoMapper: NewsListDtoMapper

    private lateinit var mockResponse: NewsFeedDto
    private lateinit var mockProfileNewsDto: NewsDto
    private lateinit var mockGroupNewsDto: NewsDto

    private lateinit var mockLikesDto: LikesDto
    private lateinit var mockCountDto: CountDto

    private lateinit var mockGroupDto: GroupDto
    private lateinit var mockProfileDto: ProfileDto

    @Before
    fun setUp() {
        dtoMapper = NewsListDtoMapper()

        photoUrl = postFactory.createSizeDto().url

        mockLikesDto = postFactory.createLikesDto()
        mockCountDto = postFactory.createCountDto()

        mockProfileDto = postFactory.createProfileDto()
        mockGroupDto = postFactory.createGroupDto()

        mockProfileNewsDto = postFactory.createNewsDto(mockProfileDto.id)
        mockGroupNewsDto = postFactory.createNewsDto(mockGroupDto.id)

        mockResponse = NewsFeedDto(
            items = listOf(mockGroupNewsDto, mockProfileNewsDto),
            groups = listOf(mockGroupDto),
            profiles = listOf(mockProfileDto),
            nextFrom = "5/5_-33393308_676827:1605157195:5"
        )
    }

    @Test
    fun checkOrder() {
        val entityList = dtoMapper.map(mockResponse)

        assertEquals(mockResponse.items.size, entityList.size)
        assertEquals(mockGroupNewsDto.id, entityList[0].id)
        assertEquals(mockProfileNewsDto.id, entityList[1].id)
    }

    @Test
    fun mapNewsFromGroup() {
        val entityList = dtoMapper.map(mockResponse)
        val postEntity: PostEntity = entityList[0]

        assertEquals(mockGroupDto.id, postEntity.sourceId)
        assertEquals(mockGroupDto.name, postEntity.sourceName)
        assertEquals(mockGroupDto.photo100, postEntity.avatarUrl)

        assertEquals(mockGroupNewsDto.date, postEntity.date.time / 1000L)
        assertEquals(mockGroupNewsDto.text, postEntity.content)

        assertEquals(photoUrl, postEntity.photoUrl)

        testMapCounters(postEntity)
    }

    @Test
    fun mapNewsFromUser() {
        val entityList = dtoMapper.map(mockResponse)
        val postEntity: PostEntity = entityList[1]

        assertEquals(mockProfileDto.id, postEntity.sourceId)
        assertEquals(mockProfileDto.getProfileName(), postEntity.sourceName)
        assertEquals(mockProfileDto.photo100, postEntity.avatarUrl)

        assertEquals(mockProfileNewsDto.date, postEntity.date.time / 1000L)
        assertEquals(mockProfileNewsDto.text, postEntity.content)

        assertEquals(photoUrl, postEntity.photoUrl)

        testMapCounters(postEntity)
    }

    private fun testMapCounters(post: PostEntity) {
        assertEquals(mockLikesDto.userLikes == 1, post.isFavorite)
        assertEquals(mockLikesDto.count, post.likes)

        assertEquals(mockCountDto.count, post.comments)
        assertEquals(mockCountDto.count, post.shares)
    }
}
