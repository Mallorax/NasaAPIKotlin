package com.example.spaceinformer.ui.details

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.descriptionText.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        observeItem()
        observeLoading()
        observeErrors()

        setUpListener()

        viewModel.getSpecificIV(args.nasaId)

        return binding.root
    }

    private fun observeLoading(){
        viewModel.loading.observe(this.viewLifecycleOwner, Observer {
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun observeItem(){
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
    }

    private fun observeErrors(){
        viewModel.errorNotification.observe(this.viewLifecycleOwner, Observer {
            if (it != null){
                Snackbar.make(this.requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun setUpListener(){
        binding.favouriteImageView.setOnClickListener {
            viewModel.toggleFavourite()
            viewModel.updateFavourite()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}