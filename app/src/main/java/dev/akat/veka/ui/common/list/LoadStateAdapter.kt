package dev.akat.veka.ui.common.list

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import dev.akat.veka.ui.common.list.viewholders.LoadStateViewHolder

class LoadStateAdapter(
    private val retryCallback: () -> Unit,
) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadStateViewHolder {
        return LoadStateViewHolder(parent) { retryCallback() }
    }
}
