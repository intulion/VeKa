package dev.akat.veka.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseFragment
import dev.akat.veka.ui.common.list.FeedAdapter
import dev.akat.veka.ui.common.list.FeedEventCallback
import dev.akat.veka.ui.common.list.ItemTouchHelperCallback
import dev.akat.veka.widget.post.PostEventCallback
import kotlinx.android.synthetic.main.fragment_feed_favorites.*
import javax.inject.Inject

class FeedFavoritesFragment : BaseFragment(R.layout.fragment_feed_favorites),
    PostEventCallback, FeedEventCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: FeedFavoritesViewModel by viewModels { viewModelFactory }

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun onDestroyView() {
        postRecycler.adapter = null
        super.onDestroyView()
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    override fun observeData() {
        viewModel.postList.observe(viewLifecycleOwner) { pagingData ->
            feedAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun initRecyclerView() {
        postRecycler.adapter = feedAdapter

        ItemTouchHelper(
            ItemTouchHelperCallback(feedAdapter)
        ).attachToRecyclerView(postRecycler)
    }

    override fun onPhotoClicked(post: Post) {
        postFragmentInteractor?.onPhotoClicked(post.photoUrl)
    }

    override fun onLikeClicked(post: Post) {
        viewModel.setLike(post)
    }

    override fun onCommentClicked(post: Post) {
        postFragmentInteractor?.onPostClicked(post.id)
    }

    override fun onShareClicked(post: Post) {
        postFragmentInteractor?.onShareClicked(post.getShareUrl())
    }

    override fun onItemClicked(postId: Int) {
        postFragmentInteractor?.onPostClicked(postId)
    }

    override fun onItemDismiss(position: Int, post: Post) {
        viewModel.removePost(post)
    }

    override fun onItemLiked(position: Int, post: Post) {
        viewModel.setLike(post)
    }
}
