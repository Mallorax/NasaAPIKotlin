package com.example.spaceinformer.ui.imagesandvideos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListFragmentBinding
import com.google.android.material.snackbar.Snackbar
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
        var adapter = IVListAdapter(IVListAdapter.OnImageClickListener{i, v ->
            Snackbar.make(requireContext(), v, "Item clicked: " + i?.data?.first()?.title, Snackbar.LENGTH_LONG).show()
        }, IVListAdapter.OnFavouriteClickListener{item, view ->
            if (item?.data?.first()?.favourite == false){
                view.setImageResource(R.drawable.ic_baseline_favorite_24)
                Snackbar.make(requireContext(), view, "Favorite: " + item?.data?.first()?.title, Snackbar.LENGTH_LONG).show()
                item?.data?.first()?.favourite = true
            }else{
                view.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Snackbar.make(requireContext(), view, "Not favorite: " + item?.data?.first()?.title, Snackbar.LENGTH_LONG).show()
                item?.data?.first()?.favourite = false
            }
        })
        ivViewModel.getIVsFromYear(2021).observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        binding.ivListRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.ivListRecycler.adapter = adapter
        return binding.root
    }

}