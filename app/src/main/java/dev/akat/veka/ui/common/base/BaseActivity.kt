package dev.akat.veka.ui.common.base

import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import dev.akat.veka.R
import dev.akat.veka.ui.common.ErrorDialogFragment

abstract class BaseActivity(@LayoutRes contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    fun showNoNetworkMessage() {
        Toast.makeText(this, R.string.message_no_network, Toast.LENGTH_SHORT).show()
    }

    fun showMessage(@StringRes messageRes: Int) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    fun showErrorDialog(@StringRes messageRes: Int) {
        if (supportFragmentManager.findFragmentByTag(ErrorDialogFragment.TAG) == null) {
            ErrorDialogFragment.newInstance(messageRes)
                .show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }
}
