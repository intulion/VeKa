package dev.akat.veka.ui.common.list.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import dev.akat.veka.R
import kotlinx.android.synthetic.main.item_network_state.view.*

class LoadStateViewHolder(
    parent: ViewGroup,
    private val retryCallback: () -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false)
) {

    init {
        itemView.retryButton.setOnClickListener { retryCallback() }
    }

    fun bind(loadState: LoadState) {
        with(itemView) {
            progressBar.isInvisible = loadState !is LoadState.Loading
            retryButton.isInvisible = loadState !is LoadState.Error
        }
    }
}
