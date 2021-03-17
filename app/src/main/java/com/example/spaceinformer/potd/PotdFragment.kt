package com.example.spaceinformer.potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.example.spaceinformer.databinding.FragmentPotdBinding
import javax.inject.Inject

class PotdFragment: Fragment() {

    private val viewModel: PotdViewModel by lazy {
        ViewModelProviders.of(this).get(PotdViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = FragmentPotdBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.potdViewModel = viewModel
        return binding.root
    }
}