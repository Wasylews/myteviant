package com.genius.wasylews.myteviant.ui.main.payments

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.genius.wasylews.myteviant.R
import com.genius.wasylews.myteviant.databinding.PaymentsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.payments_fragment) {

    private val binding: PaymentsFragmentBinding by viewBinding()
    private val viewModel: PaymentsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
    }
}