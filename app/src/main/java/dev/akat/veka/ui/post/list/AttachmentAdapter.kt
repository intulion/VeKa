package dev.akat.veka.ui.post.list

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.akat.veka.R
import kotlinx.android.synthetic.main.item_photo_attachment.view.*

class AttachmentAdapter : RecyclerView.Adapter<AttachmentViewHolder>() {

    var items: MutableList<Uri> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachmentViewHolder =
        AttachmentViewHolder(parent) { uri ->
            val index = items.indexOf(uri)
            items.removeAt(index)
            notifyItemRemoved(index)
        }

    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun addItem(newItem: Uri) {
        items.add(newItem)
        notifyItemInserted(items.lastIndex)
    }
}

class AttachmentViewHolder(
    parent: ViewGroup,
    private val onRemoveCallback: (Uri) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_photo_attachment, parent, false)
) {

    fun bind(uri: Uri) {
        with(itemView) {
            attachmentRemoveButton.setOnClickListener { onRemoveCallback.invoke(uri) }

            attachmentImageView.clipToOutline = true
            Glide.with(context)
                .load(uri)
                .into(attachmentImageView)
        }
    }
}
