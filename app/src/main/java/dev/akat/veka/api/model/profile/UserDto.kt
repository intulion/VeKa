package dev.akat.veka.api.model.profile

import com.google.gson.annotations.SerializedName

class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("domain") val domain: String?,
    @SerializedName("photo_100") val photo100: String?,
    @SerializedName("bdate") val bdate: String?,
    @SerializedName("city") val city: CityDto?,
    @SerializedName("country") val country: CountryDto?,
    @SerializedName("about") val about: String?,
    @SerializedName("last_seen") val lastSeen: LastSeenDto?,
    @SerializedName("counters") val counters: CountersDto?,
    @SerializedName("career") val career: List<CareerDto>?,
    @SerializedName("university_name") val universityName: String?,
    @SerializedName("faculty_name") val facultyName: String?,
    @SerializedName("graduation") val graduation: String?,
    @SerializedName("education_form") val educationForm: String?,
    @SerializedName("education_status") val educationStatus: String?,
)
