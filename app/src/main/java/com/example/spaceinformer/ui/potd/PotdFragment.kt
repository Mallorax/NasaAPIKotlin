package com.example.spaceinformer.ui.potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.spaceinformer.R

import com.example.spaceinformer.databinding.FragmentPotdBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        //bindings
        val baseBinding = FragmentPotdBinding.inflate(inflater)
        baseBinding.lifecycleOwner = this
        baseBinding.potdViewModel = viewModel

        val player = SimpleExoPlayer.Builder(requireContext()).build()
        baseBinding.videoPlayer.player = player
        return baseBinding.root
    }
}