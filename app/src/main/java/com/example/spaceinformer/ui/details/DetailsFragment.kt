package com.example.spaceinformer.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.DetailsFragmentBinding
import com.example.spaceinformer.databinding.FragmentPotdBinding
import com.example.spaceinformer.ui.imagesandvideos.IVListFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        viewModel.getSpecificIV(args.nasaId).observe(this.viewLifecycleOwner, Observer { item ->
            val data = item.data?.data?.first()
            binding.descriptionText.text = data?.description
            binding.titleText.text = data?.title
            Picasso.get().load(item.data?.imageLinks?.first()?.href).into(binding.mainImageView)
        })
        viewModel.isCurrentDataFavourite(args.nasaId).observe(this.viewLifecycleOwner, Observer { isFavourite ->
            if (isFavourite){
                binding.favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else if (!isFavourite){
                binding.favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}