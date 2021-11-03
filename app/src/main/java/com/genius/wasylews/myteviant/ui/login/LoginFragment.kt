package com.genius.wasylews.myteviant.ui.login

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.genius.wasylews.myteviant.R
import com.genius.wasylews.myteviant.common.ui.observe
import com.genius.wasylews.myteviant.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private val binding: LoginFragmentBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupView() {
        binding.loginEt.doAfterTextChanged {
            viewModel.usernameFlow.value = it.toString()
        }

        binding.passwordEt.doAfterTextChanged {
            viewModel.passwordFlow.value = it.toString()
        }

        binding.loginBtn.setOnClickListener {
            viewModel.login()
        }
    }

    private fun setupObservers() {
        observe(viewModel.loginEnabledFLow) {
            binding.loginBtn.isEnabled = it
        }

        observe(viewModel.loadingFlow) {
            binding.loadingProgress.isVisible = it
        }

        observe(viewModel.messageLiveData) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}