package com.example.spaceinformer.Potd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceinformer.databinding.FragmentPotdBinding

class PotdFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val binding = FragmentPotdBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }
}