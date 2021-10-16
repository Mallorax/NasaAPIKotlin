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
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

//Images & Videos
@AndroidEntryPoint
class IVListFragment : Fragment() {

    private val ivViewModel: IVViewModel by viewModels()
    private var _binding: IvListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IvListFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val adapter = setUpRecyclerViewAdapter()
        val recycler = binding.ivListRecycler

        recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        ivViewModel.getIVs(2021)

        showListOfData(adapter)
        handleLoading()
        showError()

        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)){
                    ivViewModel.getIVs(2021)
                }
            }
        })
        return binding.root
    }

    private fun showListOfData(adapter: IVListAdapter){
        ivViewModel.ivs.observe(this.viewLifecycleOwner, { items ->
            adapter.submitList(items)
        })
    }

    private fun showError(){
        ivViewModel.itemsLoadingError.observe(this.viewLifecycleOwner, {
            if (it){
                Snackbar.make(this.requireView(), "Error occurred", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun handleLoading(){
        ivViewModel.loadingStatus.observe(this.viewLifecycleOwner, { isLoading ->
            if (isLoading){
                binding.progressBar.visibility = View.VISIBLE
                binding.ivListRecycler.visibility = View.GONE
            }else{
                binding.progressBar.visibility = View.GONE
                binding.ivListRecycler.visibility = View.VISIBLE
            }
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