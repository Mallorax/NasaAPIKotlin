package com.example.spaceinformer.ui.imagesandvideos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

//Images & Videos
@AndroidEntryPoint
class IVListFragment : Fragment() {

    private val ivViewModel: IVViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = IvListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.ivViewModel = ivViewModel
        var adapter = IVListAdapter(IVListAdapter.OnClickListener { _, _ ->
            Log.v("CLICK", "Item clicked:")
        })
        ivViewModel.getIVsFromYear(2021).observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        binding.ivListRecycler.adapter = adapter
        binding.bottomNavigationView.bottomNavigationView.selectedItemId = R.id.bottom_nav_iv_collection
        return binding.root
    }

}