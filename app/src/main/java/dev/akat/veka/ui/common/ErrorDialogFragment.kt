package dev.akat.veka.ui.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import dev.akat.veka.R

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val messageRes = requireArguments().getInt(ARG_MESSAGE)

        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.label_error)
            .setMessage(messageRes)
            .setPositiveButton(R.string.label_ok) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    companion object {
        const val TAG = "ErrorDialog"
        private const val ARG_MESSAGE = "message"

        fun newInstance(@StringRes messageRes: Int): DialogFragment {
            return ErrorDialogFragment().apply {
                arguments = bundleOf(
                    ARG_MESSAGE to messageRes
                )
            }
        }
    }
}
