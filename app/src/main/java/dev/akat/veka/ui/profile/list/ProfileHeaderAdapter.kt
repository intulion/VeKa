package dev.akat.veka.ui.profile.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.akat.veka.model.Profile

class ProfileHeaderAdapter : RecyclerView.Adapter<ProfileHeaderViewHolder>() {

    var item: Profile? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHeaderViewHolder =
        ProfileHeaderViewHolder(parent)

    override fun onBindViewHolder(holder: ProfileHeaderViewHolder, position: Int) {
        item?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int =
        if (item != null) 1 else 0
}
