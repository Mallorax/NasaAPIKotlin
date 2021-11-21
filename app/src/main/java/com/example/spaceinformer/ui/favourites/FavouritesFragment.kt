package com.example.spaceinformer.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import com.example.spaceinformer.ui.imagesandvideos.IVListAdapter
import com.example.spaceinformer.ui.imagesandvideos.IVListFragmentDirections
import com.example.spaceinformer.ui.imagesandvideos.IVViewModel
import com.example.spaceinformer.ui.onItemFavouriteClick
import com.example.spaceinformer.ui.onItemImageClick
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private var _binding: IvListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IvListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = setUpRecyclerViewAdapter()
        val recycler = binding.ivListRecycler

        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        favouritesViewModel.favourites.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
        recycler.adapter = adapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favouritesViewModel.loadFavourites()
    }


    private fun setUpRecyclerViewAdapter(): IVListAdapter {
        return IVListAdapter(IVListAdapter.OnImageClickListener { item, view ->
            val isNavViable = onItemImageClick(item, view)
            if (isNavViable){
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(item!!.nasaId)
                view.findNavController().navigate(action)
            }
        }, IVListAdapter.OnFavouriteClickListener { item, view ->
            favouritesViewModel.updateFavourite(onItemFavouriteClick(item, view))
        }, requireContext().applicationContext)
    }
}