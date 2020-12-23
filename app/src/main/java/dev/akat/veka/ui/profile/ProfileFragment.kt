package dev.akat.veka.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseFragment
import dev.akat.veka.ui.common.list.FeedAdapter
import dev.akat.veka.ui.common.list.FeedEventCallback
import dev.akat.veka.ui.common.list.LoadStateAdapter
import dev.akat.veka.ui.profile.list.ProfileHeaderAdapter
import dev.akat.veka.widget.post.PostEventCallback
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment(R.layout.fragment_profile),
    PostEventCallback, FeedEventCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: ProfileViewModel by viewModels { viewModelFactory }

    private val feedAdapter: FeedAdapter by lazy { FeedAdapter(this, this) }
    private val profileAdapter: ProfileHeaderAdapter by lazy { ProfileHeaderAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initFab()
    }

    override fun observeData() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            profileAdapter.item = profile
        }

        viewModel.postList.observe(viewLifecycleOwner) { pagingData ->
            feedAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onDestroyView() {
        profileRecycler.adapter = null
        super.onDestroyView()
    }

    private fun initRecyclerView() {
        profileRecycler.adapter = ConcatAdapter(
            profileAdapter,
            feedAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { feedAdapter.retry() }
            ),
        )
    }

    private fun initFab() {
        createPostFab.setOnClickListener {
            postFragmentInteractor?.onCreatePostClicked()
        }
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
