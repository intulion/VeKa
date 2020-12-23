package dev.akat.veka.ui.profile.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.akat.veka.R
import dev.akat.veka.model.Profile
import kotlinx.android.synthetic.main.item_user_profile.view.*

class ProfileHeaderViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_user_profile, parent, false)
) {

    fun bind(profile: Profile) {
        with(itemView) {
            profileAvatar.clipToOutline = true
            Glide.with(context)
                .load(profile.avatarUrl)
                .into(profileAvatar)

            profileName.text = profile.name
            profileAbout.text = profile.about
            profileLastSeen.text =
                context.getString(R.string.label_profile_last_seen, profile.lastSeen)

            profileBirthday.text =
                context.getString(R.string.label_profile_birthday, profile.birthday)
            profileLocation.text =
                context.getString(R.string.label_profile_location, profile.getLocation())
            profileEducation.text =
                context.getString(R.string.label_profile_education, profile.university)
            profileJob.text = context.getString(R.string.label_profile_job, profile.company)

            profileFriends.text = profile.friends.toString()
            profileFollowers.text = profile.followers.toString()
            profilePages.text = profile.pages.toString()
        }
    }
}
