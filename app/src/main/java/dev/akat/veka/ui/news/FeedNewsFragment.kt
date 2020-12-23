package dev.akat.veka.ui.news

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState.Error
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseFragment
import dev.akat.veka.ui.common.list.FeedAdapter
import dev.akat.veka.ui.common.list.FeedEventCallback
import dev.akat.veka.ui.common.list.FeedItemDecoration
import dev.akat.veka.ui.common.list.ItemTouchHelperCallback
import dev.akat.veka.ui.common.list.LoadStateAdapter
import dev.akat.veka.widget.post.PostEventCallback
import kotlinx.android.synthetic.main.fragment_feed_news.*
import kotlinx.android.synthetic.main.include_empty_state.*
import kotlinx.android.synthetic.main.include_loading_state.*
import javax.inject.Inject

class FeedNewsFragment : BaseFragment(R.layout.fragment_feed_news),
    PostEventCallback, FeedEventCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: FeedNewsViewModel by viewModels { viewModelFactory }

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(this, this) }

    private val loadStateListener = { loadState: CombinedLoadStates ->
        val isListEmpty = (feedAdapter.itemCount == 0)
        val state = loadState.refresh

        if (state !is Loading) swipeRefresh.isRefreshing = false

        showLoadingScreen(state is Loading && isListEmpty)
        showErrorScreen(state is Error && isListEmpty)
        showContent(state is NotLoading || !isListEmpty)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        swipeRefresh?.isEnabled = true
    }

    override fun onDetach() {
        swipeRefresh?.isEnabled = false
        super.onDetach()
    }

    override fun onDestroyView() {
        postRecycler.adapter = null

        feedAdapter.removeLoadStateListener(loadStateListener)
        super.onDestroyView()
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initRecyclerView()
        initSwipeRefresh()
        initFab()
    }

    override fun observeData() {
        viewModel.postList.observe(viewLifecycleOwner) { pagingData ->
            feedAdapter.submitData(lifecycle, pagingData)
        }
    }

    private fun initAdapter() {
        feedAdapter.addLoadStateListener(loadStateListener)
    }

    private fun initRecyclerView() {
        with(postRecycler) {
            adapter = feedAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { feedAdapter.retry() }
            )
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                FeedItemDecoration(resources.getDimensionPixelSize(R.dimen.section_separator_height))
            )
        }

        ItemTouchHelper(
            ItemTouchHelperCallback(feedAdapter)
        ).attachToRecyclerView(postRecycler)
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            feedAdapter.refresh()
        }
    }

    private fun initFab() {
        createPostFab.setOnClickListener {
            postFragmentInteractor?.onCreatePostClicked()
        }
    }

    private fun showLoadingScreen(show: Boolean) {
        loadingState.isVisible = show
        if (show) loadingState.startShimmer() else loadingState.stopShimmer()
    }

    private fun showErrorScreen(show: Boolean) {
        errorState.isVisible = show
    }

    private fun showContent(show: Boolean) {
        postRecycler.isVisible = show
        createPostFab.isVisible = show
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
