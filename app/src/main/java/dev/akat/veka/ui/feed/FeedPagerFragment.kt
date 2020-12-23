package dev.akat.veka.ui.feed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.ui.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_feed_pager.*
import javax.inject.Inject

class FeedPagerFragment : BaseFragment(R.layout.fragment_feed_pager) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: FeedPagerViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        feedPager.adapter = FeedPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        feedPager.isUserInputEnabled = false

        TabLayoutMediator(feedBottomTab, feedPager) { tab, position ->
            when (position) {
                TAB_PROFILE -> {
                    tab.setText(R.string.label_profile)
                    tab.setIcon(R.drawable.ic_profile_24)
                }
                TAB_NEWS -> {
                    tab.setText(R.string.label_news)
                    tab.setIcon(R.drawable.ic_fire_24)
                }
                TAB_FAVORITES -> {
                    tab.setText(R.string.label_favorite)
                    tab.setIcon(R.drawable.ic_like_24)
                }
            }
        }.attach()
    }

    override fun observeData() {
        viewModel.favoritesCount.observe(viewLifecycleOwner) { count ->
            if (count == 0) {
                (feedPager.adapter as FeedPagerAdapter).hideFavorites()
            } else {
                (feedPager.adapter as FeedPagerAdapter).showFavorites()
            }
        }
    }

    companion object {
        const val TAG = "FeedPagerFragment"

        private const val TAB_PROFILE = 0
        private const val TAB_NEWS = 1
        private const val TAB_FAVORITES = 2
    }
}
