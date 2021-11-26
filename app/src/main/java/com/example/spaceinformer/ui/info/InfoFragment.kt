package com.example.spaceinformer.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spaceinformer.databinding.InfoFragmentBinding

class InfoFragment: Fragment() {

    private var _binding: InfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InfoFragmentBinding.inflate(inflater)
        return binding.root
    }
}