package com.example.spaceinformer.ui.potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.spaceinformer.databinding.FragmentPotdBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PotdFragment: Fragment() {


    private val  viewModel: PotdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = FragmentPotdBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.potdViewModel = viewModel
        val player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.videoPlayer.player = player
        return binding.root
    }
}