package com.example.spaceinformer.ui.potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.spaceinformer.databinding.PotdFragmentBinding

import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PotdFragment: Fragment() {


    private val  viewModel: PotdViewModel by viewModels()
    private var _baseBinding: PotdFragmentBinding? = null
    private val baseBinding get() = _baseBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        //bindings
        _baseBinding = PotdFragmentBinding.inflate(inflater)
        baseBinding.lifecycleOwner = viewLifecycleOwner
        baseBinding.potdViewModel = viewModel

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

    override fun onDestroyView() {
        super.onDestroyView()
        _baseBinding = null
    }
}