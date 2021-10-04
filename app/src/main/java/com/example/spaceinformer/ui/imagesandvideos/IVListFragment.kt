package com.example.spaceinformer.ui.imagesandvideos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//Images & Videos
@AndroidEntryPoint
class IVListFragment : Fragment() {

    private val ivViewModel: IVViewModel by viewModels()
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = IvListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.ivViewModel = ivViewModel
        val adapter = setUpRecyclerViewAdapter()
        val recycler = binding.ivListRecycler
        submitFirstPage(adapter)
        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    page++
                    changePage(page, adapter)
                }
            }
        })
        return binding.root
    }

    private fun changePage(page: Int, adapter: IVListAdapter){
        ivViewModel.getIVsFromYear(2021, page).observe(viewLifecycleOwner, {
            val list = mutableListOf<IvItem>()
            list.addAll(adapter.currentList)
            list.addAll(it)
            adapter.submitList(list)
            adapter.notifyDataSetChanged()
        })
    }

    private fun submitFirstPage(adapter: IVListAdapter){
        ivViewModel.getIVsFromYear(2021, 1).observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


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
                val action = IVListFragmentDirections.actionIVListFragmentToDetailsFragment(nasaID)
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
                ivViewModel.saveFavourite(item.data!!.first())
            } else {
                view.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Snackbar.make(
                    requireContext(),
                    view,
                    "Not favorite: " + item?.data?.first()?.title,
                    Snackbar.LENGTH_LONG
                ).show()
                item?.data?.first()?.favourite = false
                ivViewModel.saveFavourite(item?.data!!.first())
            }
        })
    }

}