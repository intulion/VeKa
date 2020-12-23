package dev.akat.veka.ui.post.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.akat.veka.R
import kotlinx.android.synthetic.main.item_attachment_control.view.*

class AttachmentControlsAdapter(
    private val onAddClickCallback: () -> Unit,
) : RecyclerView.Adapter<AttachmentFooterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentFooterViewHolder =
        AttachmentFooterViewHolder(parent, onAddClickCallback)

    override fun onBindViewHolder(holder: AttachmentFooterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 1
}

class AttachmentFooterViewHolder(
    parent: ViewGroup,
    private val onClickCallback: () -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_attachment_control, parent, false)
) {

    fun bind() {
        itemView.attachmentAddButton.setOnClickListener { onClickCallback.invoke() }
    }
}
