package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.profile.UserDto
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.utils.ProfileFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserDtoMapperTest {

    private val profileFactory = ProfileFactory()

    private lateinit var dtoMapper: UserDtoMapper

    private lateinit var mockUserDto: UserDto
    private lateinit var mockProfileEntity: ProfileEntity

    @Before
    fun setUp() {
        dtoMapper = UserDtoMapper()

        mockUserDto = profileFactory.createUserDto("Square")
        mockProfileEntity = profileFactory.createProfileEntity("Square")
    }

    @Test
    fun mapUser() {
        assertEquals(mockProfileEntity, dtoMapper.map(mockUserDto))
    }
}
