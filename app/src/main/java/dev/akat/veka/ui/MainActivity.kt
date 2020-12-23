package dev.akat.veka.ui

import android.content.Intent
import android.os.Bundle
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import dev.akat.veka.VeKaApp
import dev.akat.veka.R
import dev.akat.veka.api.AuthManager
import dev.akat.veka.ui.common.ErrorDialogFragment
import dev.akat.veka.ui.common.PostFragmentInteractor
import dev.akat.veka.ui.common.base.BaseActivity
import dev.akat.veka.ui.detail.PostDetailFragment
import dev.akat.veka.ui.feed.FeedPagerFragment
import dev.akat.veka.ui.login.LoginFragment
import dev.akat.veka.ui.photo.PostPhotoFragment
import dev.akat.veka.ui.post.CreatePostFragment
import javax.inject.Inject

class MainActivity : BaseActivity(R.layout.activity_main), PostFragmentInteractor {

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as VeKaApp).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        openNextScreen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                authManager.credentials = AuthManager.Credentials(token.accessToken, token.created)
            }

            override fun onLoginFailed(errorCode: Int) {
                showMessage(R.string.label_login_failed)
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onPostClicked(postId: Int) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fragment_open_enter,
                R.anim.fragment_open_exit,
                R.anim.fragment_close_enter,
                R.anim.fragment_close_exit
            )
            .replace(R.id.fragmentContainer,
                PostDetailFragment.newInstance(postId),
                PostDetailFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onPhotoClicked(postUrl: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                PostPhotoFragment.newInstance(postUrl),
                PostPhotoFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onErrorOccurred(messageRes: Int) {
        if (supportFragmentManager.findFragmentByTag(ErrorDialogFragment.TAG) == null) {
            ErrorDialogFragment.newInstance(messageRes)
                .show(supportFragmentManager, ErrorDialogFragment.TAG)
        }
    }

    override fun onShareClicked(postUrl: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, postUrl)
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }

    override fun onCreatePostClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,
                CreatePostFragment.newInstance(),
                CreatePostFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    private fun openNextScreen() {
        if (authManager.isLoggedIn()) {
            if (supportFragmentManager.findFragmentByTag(FeedPagerFragment.TAG) == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FeedPagerFragment(), FeedPagerFragment.TAG)
                    .commit()
            }
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment(), LoginFragment.TAG)
                .commit()
        }
    }
}
