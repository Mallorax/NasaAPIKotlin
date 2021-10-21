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
        handleLoading()

        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        favouritesViewModel.favourites.observe(viewLifecycleOwner, {
            val count = adapter.itemCount
            adapter.submitList(it)
            adapter.notifyItemInserted(count)
        })
        recycler.adapter = adapter
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favouritesViewModel.loadFavourites()
    }

    private fun handleLoading(){
        favouritesViewModel.loadingStatus.observe(this.viewLifecycleOwner, { isLoading ->
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
                binding.ivListRecycler.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
                binding.ivListRecycler.visibility = View.VISIBLE
            }
        })
    }

    //TODO: This code is duplicated, get rid of that somehow
    private fun setUpRecyclerViewAdapter(): IVListAdapter {
        return IVListAdapter(IVListAdapter.OnImageClickListener { item, view ->
            val nasaID = item?.data?.first()?.nasaId
            if (nasaID != null) {
                Snackbar.make(
                    requireContext(),
                    view,
                    "Navigation to details of: " + item.data?.first()?.title,
                    Snackbar.LENGTH_LONG
                ).show()
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToDetailsFragment(nasaID)
                view.findNavController().navigate(action)
            } else {
                Snackbar.make(
                    requireContext(),
                    view,
                    "Id of an item not found: " + item?.data?.first()?.title,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }, IVListAdapter.OnFavouriteClickListener { item, view ->
            if (item?.data?.first()?.favourite == false) {
                view.setImageResource(R.drawable.ic_baseline_favorite_24)
                Snackbar.make(
                    requireContext(),
                    view,
                    "Favorite: " + item.data?.first()?.title,
                    Snackbar.LENGTH_LONG
                ).show()
                item.data?.first()?.favourite = true
            } else {
                view.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Snackbar.make(
                    requireContext(),
                    view,
                    "Not favorite: " + item?.data?.first()?.title,
                    Snackbar.LENGTH_LONG
                ).show()
                item?.data?.first()?.favourite = false
            }
        })
    }
}