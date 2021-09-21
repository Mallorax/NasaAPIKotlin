package com.example.spaceinformer.ui.potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.spaceinformer.R

import com.example.spaceinformer.databinding.FragmentPotdBinding
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PotdFragment: Fragment() {


    private val  viewModel: PotdViewModel by viewModels()
    private lateinit var baseBinding: FragmentPotdBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        //bindings
        baseBinding = FragmentPotdBinding.inflate(inflater)
        baseBinding.lifecycleOwner = this
        baseBinding.potdViewModel = viewModel

        val player = SimpleExoPlayer.Builder(requireContext()).build()
        baseBinding.videoPlayer.player = player
        setUpObservers()
        return baseBinding.root
    }

    private fun setUpObservers(){
        viewModel.fetchPotd()
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it){
                baseBinding.progressBar.visibility = View.VISIBLE
                baseBinding.videoPlayer.visibility = View.GONE
                baseBinding.potdImageView.visibility = View.GONE
            }else{
                baseBinding.progressBar.visibility = View.GONE
                baseBinding.potdImageView.visibility = View.VISIBLE
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })
    }
}