package dev.akat.veka.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.model.Post
import dev.akat.veka.ui.common.base.BaseFragment
import dev.akat.veka.ui.common.list.LoadStateAdapter
import dev.akat.veka.ui.detail.list.CommentAdapter
import dev.akat.veka.ui.detail.list.PostHeaderAdapter
import dev.akat.veka.utils.Result
import dev.akat.veka.utils.hideSoftKeyboard
import dev.akat.veka.utils.setEnabledState
import dev.akat.veka.widget.post.PostEventCallback
import kotlinx.android.synthetic.main.fragment_post_detail.*
import javax.inject.Inject

class PostDetailFragment : BaseFragment(R.layout.fragment_post_detail),
    PostEventCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: PostDetailViewModel by viewModels { viewModelFactory }

    private val commentAdapter: CommentAdapter by lazy { CommentAdapter() }
    private val postAdapter: PostHeaderAdapter by lazy { PostHeaderAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun onDestroyView() {
        detailRecycler.adapter = null
        super.onDestroyView()
    }

    override fun observeData() {
        val postId = requireArguments().getInt(ARG_POST_ID)
        viewModel.observePost(postId)

        viewModel.currentPost.observe(viewLifecycleOwner) { post ->
            postAdapter.item = post
        }

        viewModel.commentList.observe(viewLifecycleOwner) { pagingData ->
            commentAdapter.submitData(lifecycle, pagingData)
        }

        viewModel.state.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { state ->
                if (state is Result.Success) onSuccessfulSend()
            }
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initAddComment()
    }

    private fun initRecyclerView() {
        detailRecycler.adapter = ConcatAdapter(
            postAdapter,
            commentAdapter.withLoadStateFooter(
                footer = LoadStateAdapter { commentAdapter.retry() }
            ),
        )
    }

    private fun initAddComment() {
        detailSendButton.setEnabledState(detailCommentEditText.text.isNotBlank())

        detailCommentEditText.doAfterTextChanged { text ->
            detailSendButton.setEnabledState(text?.isNotBlank() ?: false)
        }

        detailSendButton.setOnClickListener {
            val message = detailCommentEditText.text.toString()
            if (message.isNotBlank()) {
                postAdapter.item?.let { viewModel.sendComment(it, message) }
            }
        }
    }

    private fun onSuccessfulSend() {
        detailCommentEditText.text.clear()
        detailCommentEditText.hideSoftKeyboard()

        showMessage(R.string.message_successfully_send)

        commentAdapter.refresh()
    }

    override fun onPhotoClicked(post: Post) {
        postFragmentInteractor?.onPhotoClicked(post.photoUrl)
    }

    override fun onLikeClicked(post: Post) {
        viewModel.setLike(post)
    }

    override fun onCommentClicked(post: Post) {
        (detailRecycler.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(1, 0)
    }

    override fun onShareClicked(post: Post) {
        postFragmentInteractor?.onShareClicked(post.getShareUrl())
    }

    companion object {
        const val TAG = "PostDetailFragment"

        private const val ARG_POST_ID = "post_id"

        fun newInstance(postId: Int): Fragment {
            return PostDetailFragment().apply {
                arguments = bundleOf(
                    ARG_POST_ID to postId
                )
            }
        }
    }
}
