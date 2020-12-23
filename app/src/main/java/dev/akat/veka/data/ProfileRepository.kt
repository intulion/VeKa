package dev.akat.veka.data

import dev.akat.veka.api.NetworkDataSource
import dev.akat.veka.api.model.ApiListResponse
import dev.akat.veka.api.model.posts.GroupDto
import dev.akat.veka.api.model.profile.UserDto
import dev.akat.veka.local.LocalDataSource
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.mappers.api.UserDtoMapper
import dev.akat.veka.mappers.db.ProfileEntityMapper
import dev.akat.veka.model.Profile
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource,
    private val userDtoMapper: UserDtoMapper,
    private val profileEntityMapper: ProfileEntityMapper,
) {

    fun fetchUserProfile(): Single<ProfileEntity> =
        networkDataSource.fetchCurrentUser()
            .flatMap(::fillCompanyAndMapToEntity)

    fun getUserProfile(): Observable<Profile> =
        localDataSource.getProfile()
            .map(profileEntityMapper::map)

    fun saveProfile(profile: ProfileEntity) {
        localDataSource.addProfile(profile)
    }

    private fun fetchGroupById(id: Int): Single<ApiListResponse<GroupDto>> =
        networkDataSource.fetchGroupById(id)

    private fun fillCompanyAndMapToEntity(apiResponse: ApiListResponse<UserDto>): Single<ProfileEntity> {
        val userDto = apiResponse.response.first()
        val careerDto = userDto.career?.last()

        return if (careerDto?.company.isNullOrEmpty() && careerDto?.groupId != null) {
            fetchGroupById(careerDto.groupId)
                .map { groupResponse ->
                    userDtoMapper.map(userDto)
                        .apply { company = groupResponse.response.first().name }
                }
        } else {
            Single.fromCallable {
                userDtoMapper.map(userDto).apply {
                    company = careerDto?.company.orEmpty()
                }
            }
        }
    }
}
