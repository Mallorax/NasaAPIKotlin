package com.example.spaceinformer.ui.imagesandvideos

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import com.example.spaceinformer.ui.onItemFavouriteClick
import com.example.spaceinformer.ui.onItemImageClick
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//Images & Videos
@AndroidEntryPoint
class IVListFragment : Fragment() {

    private val ivViewModel: IVViewModel by viewModels()
    private var _binding: IvListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: IVListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IvListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = setUpRecyclerViewAdapter()
        val recycler = binding.ivListRecycler

        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        ivViewModel.getIVs(2021)

        showListOfData()
        showError()

        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    ivViewModel.getIVs(2021)
                }
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchViewItem = menu.findItem(R.id.iv_search)
        val searchView = searchViewItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { return true }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText != "") {
                    ivViewModel.getIvsWithSearch(newText)
                    ivViewModel.searchedIVs.observe(this@IVListFragment.viewLifecycleOwner, {
                        adapter.submitList(it)
                        adapter.notifyDataSetChanged()
                    })
                }else{
                    showListOfData()
                }
                return true
            }
        })
    }

    override fun onStart() {
        super.onStart()
    }


    private fun showListOfData() {
        ivViewModel.ivs.observe(this.viewLifecycleOwner, { items ->
            adapter.submitList(items)
            adapter.notifyDataSetChanged()
        })
    }

    private fun showError() {
        ivViewModel.itemsLoadingError.observe(this.viewLifecycleOwner, {
            if (it) {
                Snackbar.make(this.requireView(), "Error occurred", Snackbar.LENGTH_LONG).show()
            }
        })
    }


    private fun setUpRecyclerViewAdapter(): IVListAdapter {
        return IVListAdapter(IVListAdapter.OnImageClickListener { item, view ->
            val isNavViable = onItemImageClick(item, view)
            if (isNavViable){
                val action = IVListFragmentDirections.actionIVListFragmentToDetailsFragment(item!!.nasaId)
                view.findNavController().navigate(action)
            }
        }, IVListAdapter.OnFavouriteClickListener { item, view ->
            ivViewModel.saveFavourite(onItemFavouriteClick(item, view))

        })
    }

}