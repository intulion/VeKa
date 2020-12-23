package dev.akat.veka.utils

import dev.akat.veka.api.model.profile.CareerDto
import dev.akat.veka.api.model.profile.UserDto
import dev.akat.veka.local.model.ProfileEntity
import dev.akat.veka.model.Profile
import java.util.Date

class ProfileFactory {

    fun createProfile(company: String) = Profile(
        id = 42,
        name = "Jake Wharton",
        domain = "wharton",
        avatarUrl = "https://via.placeholder.com/100",
        about = "",
        city = "",
        country = "",
        lastSeen = Date(0),
        birthday = "",
        university = "",
        company = company,
        followers = 0,
        friends = 0,
        pages = 0,
    )

    fun createProfileEntity(company: String) = ProfileEntity(
        id = 42,
        name = "Jake Wharton",
        domain = "wharton",
        avatarUrl = "https://via.placeholder.com/100",
        about = "",
        city = "",
        country = "",
        lastSeen = Date(0),
        birthday = "",
        university = "",
        company = company,
        followers = 0,
        friends = 0,
        pages = 0,
    )

    fun createUserDto(company: String = "", companyGroupId: Int? = null) = UserDto(
        id = 42,
        firstName = "Jake",
        lastName = "Wharton",
        domain = "wharton",
        photo100 = "https://via.placeholder.com/100",
        bdate = null,
        city = null,
        country = null,
        about = null,
        lastSeen = null,
        counters = null,
        career = listOf(createCareerDto(company, companyGroupId)),
        universityName = null,
        facultyName = null,
        graduation = null,
        educationForm = null,
        educationStatus = null,
    )

    private fun createCareerDto(company: String, companyGroupId: Int?) = CareerDto(
        cityId = 0,
        countryId = 0,
        until = null,
        from = 0,
        company = if (companyGroupId == null) company else "",
        groupId = companyGroupId
    )
}
