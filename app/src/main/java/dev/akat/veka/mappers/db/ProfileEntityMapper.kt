package dev.akat.veka.mappers.db

import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.mappers.Mapper
import dev.akat.veka.model.Profile

class ProfileEntityMapper : Mapper<ProfileEntity, Profile> {

    override fun map(from: ProfileEntity): Profile =
        Profile(
            id = from.id,
            domain = from.domain,
            name = from.name,
            avatarUrl = from.avatarUrl,
            about = from.about,
            city = from.city,
            country = from.country,
            lastSeen = from.lastSeen,
            birthday = from.birthday,
            university = from.university,
            company = from.company,
            followers = from.followers,
            friends = from.friends,
            pages = from.pages,
        )
}
