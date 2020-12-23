package dev.akat.veka.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import dev.akat.veka.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener {
            VK.login(requireActivity(), arrayListOf(VKScope.WALL, VKScope.FRIENDS, VKScope.PHOTOS))
        }
    }

    companion object {
        const val TAG = "LoginFragment"
    }
}
