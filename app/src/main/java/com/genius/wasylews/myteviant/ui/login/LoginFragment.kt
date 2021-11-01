package com.genius.wasylews.myteviant.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.genius.wasylews.myteviant.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModels()

    companion object {
        fun newInstance() = LoginFragment()
    }
}