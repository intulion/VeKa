package dev.akat.veka.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.akat.veka.ui.favorites.FeedFavoritesFragment
import dev.akat.veka.ui.news.FeedNewsFragment
import dev.akat.veka.ui.profile.ProfileFragment

class FeedPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private val items = mutableListOf<Fragment>(
        ProfileFragment(),
        FeedNewsFragment(),
        FeedFavoritesFragment(),
    )

    override fun createFragment(position: Int) = items[position]

    override fun getItemCount() = items.size

    fun hideFavorites() {
        if (items.size > 2) {
            val lastPosition = items.lastIndex
            items.removeAt(lastPosition)
            notifyItemRemoved(lastPosition)
        }
    }

    fun showFavorites() {
        if (items.size == 2) {
            items.add(FeedFavoritesFragment())
            notifyItemInserted(items.lastIndex)
        }
    }
}
