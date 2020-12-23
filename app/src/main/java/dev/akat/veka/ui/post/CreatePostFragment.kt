package dev.akat.veka.ui.post

import android.app.Activity.RESULT_OK
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.ui.common.base.BaseFragment
import dev.akat.veka.ui.post.list.AttachmentAdapter
import dev.akat.veka.ui.post.list.AttachmentControlsAdapter
import dev.akat.veka.utils.Result
import dev.akat.veka.utils.Result.Error
import dev.akat.veka.utils.Result.Loading
import dev.akat.veka.utils.showSoftKeyboard
import kotlinx.android.synthetic.main.fragment_create_post.*
import java.util.ArrayList
import javax.inject.Inject

class CreatePostFragment : BaseFragment(R.layout.fragment_create_post) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val viewModel: CreatePostViewModel by viewModels { viewModelFactory }

    private val attachmentAdapter: AttachmentAdapter by lazy { AttachmentAdapter() }
    private val attachmentControlsAdapter: AttachmentControlsAdapter by lazy {
        AttachmentControlsAdapter {
            if (attachmentAdapter.itemCount < MAX_ATTACHMENTS) {
                val intent = Intent().apply {
                    action = Intent.ACTION_PICK
                    data = MediaStore.Images.Media.INTERNAL_CONTENT_URI
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                startActivityForResult(intent, REQUEST_IMAGE_CHOOSER)
            } else {
                showMessage(R.string.message_max_attachments)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun onDestroyView() {
        attachmentRecycler.adapter = null
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_URI_LIST,
            attachmentAdapter.items as ArrayList<Uri>)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CHOOSER) {
            data?.clipData?.let { addSelectedPhotos(it) }
        }
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        attachmentRecycler.adapter = ConcatAdapter(attachmentControlsAdapter, attachmentAdapter)

        savedInstanceState?.getParcelableArrayList<Uri>(KEY_URI_LIST)?.let { list ->
            attachmentAdapter.items = list.toMutableList()
        }

        postPublishButton.setOnClickListener {
            viewModel.createPost(postMessageEditText.text.toString(), attachmentAdapter.items)
        }

        postCloseButton.setOnClickListener { parentFragmentManager.popBackStack() }

        postMessageEditText.showSoftKeyboard()
    }

    override fun observeData() {
        viewModel.state.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { state ->
                showContent(state !is Loading)
                when (state) {
                    is Loading -> showContent(false)
                    is Error -> showMessage(state.messageRes)
                    is Result.Success -> showMessage(R.string.message_post_published)
                }
            }
        }
    }

    private fun addSelectedPhotos(clipData: ClipData) {
        for (i in 0 until clipData.itemCount) {
            if (attachmentAdapter.itemCount < MAX_ATTACHMENTS) {
                attachmentAdapter.addItem(clipData.getItemAt(i).uri)
            }
        }
    }

    private fun showContent(show: Boolean) {
        publishProgressBar.isInvisible = show

        postPublishButton.isEnabled = show
        postMessageEditText.isEnabled = show
    }

    companion object {
        const val TAG = "CreatePostFragment"

        private const val REQUEST_IMAGE_CHOOSER = 42
        private const val KEY_URI_LIST = "uri_list"

        private const val MAX_ATTACHMENTS = 5

        fun newInstance(): Fragment {
            return CreatePostFragment()
        }
    }
}
