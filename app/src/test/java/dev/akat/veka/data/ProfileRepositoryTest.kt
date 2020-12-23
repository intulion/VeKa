package dev.akat.veka.data

import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.api.model.ApiListResponse
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.mappers.api.UserDtoMapper
import dev.akat.veka.mappers.db.ProfileEntityMapper
import dev.akat.veka.model.Profile
import dev.akat.veka.utils.PostFactory
import dev.akat.veka.utils.ProfileFactory
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock

class ProfileRepositoryTest {

    private val profileFactory = ProfileFactory()
    private val postFactory = PostFactory()

    private val companyName = "Square"

    private val network = mock(NetworkDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)

    private lateinit var repository: ProfileRepository

    private lateinit var mockProfile: Profile
    private lateinit var mockProfileEntity: ProfileEntity

    @Before
    fun setUp() {
        mockProfile = profileFactory.createProfile(companyName)
        mockProfileEntity = profileFactory.createProfileEntity(companyName)

        repository = ProfileRepository(network, local, UserDtoMapper(), ProfileEntityMapper())
    }

    @Test
    fun fetchUserProfileWithCompany() {
        // company name set as string
        val mockUserDto = profileFactory.createUserDto(company = companyName)
        val mockUserResponse = ApiListResponse(
            response = listOf(mockUserDto),
        )

        `when`(network.fetchCurrentUser()).thenReturn(Single.just(mockUserResponse))

        repository.fetchUserProfile()
            .test()
            .assertNoErrors()
            .assertValue(mockProfileEntity)
    }

    @Test
    fun fetchUserProfileWithGroup() {
        // additional request - get company name from group
        val mockGroupDto = postFactory.createGroupDto()
        val mockGroupResponse = ApiListResponse(
            response = listOf(mockGroupDto),
        )

        // company name set as group id
        val mockUserDto = profileFactory.createUserDto(companyGroupId = mockGroupDto.id)
        val mockUserResponse = ApiListResponse(
            response = listOf(mockUserDto),
        )

        `when`(network.fetchCurrentUser()).thenReturn(Single.just(mockUserResponse))
        `when`(network.fetchGroupById(anyInt())).thenReturn(Single.just(mockGroupResponse))

        repository.fetchUserProfile()
            .test()
            .assertNoErrors()
            .assertValue(mockProfileEntity)
    }

    @Test
    fun getUserProfile() {
        `when`(local.getProfile()).thenReturn(Observable.just(mockProfileEntity))

        repository.getUserProfile()
            .test()
            .assertNoErrors()
            .assertValue(mockProfile)
    }
}
