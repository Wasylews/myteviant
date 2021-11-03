package com.genius.wasylews.myteviant.ui.main

import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.genius.wasylews.myteviant.R
import com.genius.wasylews.myteviant.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val mainFragmentBinding: MainFragmentBinding by viewBinding()
    private val viewModel: MainViewModel by viewModels()
}