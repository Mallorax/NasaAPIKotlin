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
import com.google.android.material.snackbar.Snackbar
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.detailedAppIvItem.observe(this.viewLifecycleOwner, Observer { data ->
            if (data != null){
                binding.descriptionText.text = data.description
                binding.titleText.text = data.title
                Picasso.get().load(data.imageThumbnail).into(binding.mainImageView)
                if (data.favourite){
                    binding.favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite_24)
                }else{
                    binding.favouriteImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        })

        viewModel.loading.observe(this.viewLifecycleOwner, Observer {
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        })

        viewModel.errorNotification.observe(this.viewLifecycleOwner, Observer {
            if (it != null){
                Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        })

        viewModel.getSpecificIV(args.nasaId)

        binding.favouriteImageView.setOnClickListener {
            viewModel.updateFavourite()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}