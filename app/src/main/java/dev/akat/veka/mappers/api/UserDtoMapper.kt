package dev.akat.veka.mappers.api

import dev.akat.veka.api.model.profile.UserDto
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.mappers.Mapper
import java.util.Date

class UserDtoMapper : Mapper<UserDto, ProfileEntity> {

    override fun map(from: UserDto): ProfileEntity =
        ProfileEntity(
            id = from.id,
            name = "${from.firstName} ${from.lastName}",
            domain = from.domain.orEmpty(),
            avatarUrl = from.photo100.orEmpty(),
            about = from.about.orEmpty(),
            city = from.city?.title.orEmpty(),
            country = from.country?.title.orEmpty(),
            lastSeen = Date((from.lastSeen?.time ?: 0L) * DATE_OFFSET),
            birthday = from.bdate.orEmpty(),
            university = from.universityName.orEmpty(),
            company = from.career?.last()?.company.orEmpty(),
            followers = from.counters?.followers ?: 0,
            friends = from.counters?.friends ?: 0,
            pages = from.counters?.pages ?: 0,
        )

    private companion object {
        const val DATE_OFFSET = 1000
    }
}
