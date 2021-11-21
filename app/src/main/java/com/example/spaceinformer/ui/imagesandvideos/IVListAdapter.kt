package com.example.spaceinformer.ui.imagesandvideos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.*
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.google.android.exoplayer2.Player
import com.google.android.material.snackbar.Snackbar


class IVListAdapter(private val onImageClickListener: OnImageClickListener,
                    private val onFavouriteClickListener: OnFavouriteClickListener,
                    private val context: Context):
    ListAdapter<DomainIvItem, RecyclerView.ViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<DomainIvItem>(){
        override fun areItemsTheSame(oldDomainIvItem: DomainIvItem, newDomainIvItem: DomainIvItem): Boolean {
            return oldDomainIvItem === newDomainIvItem
        }

        override fun areContentsTheSame(oldDomainIvItem: DomainIvItem, newDomainIvItem: DomainIvItem): Boolean {
            return oldDomainIvItem.nasaId == newDomainIvItem.nasaId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType){
            0 -> {
                val binding = IvImageItemBindingImpl.inflate(inflater, parent, false)
                IvImageViewHolder(binding)
            } 1 -> {
                val binding = IvVideoItemBindingImpl.inflate(inflater, parent, false)
                IvVideoViewHolder(binding)
            } else -> {
                val binding = IvLoadingItemBindingImpl.inflate(inflater, parent, false)
                IvLoadingViewHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holderIvImage: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holderIvImage.itemViewType){
            0 -> {
                val holder = holderIvImage as IvImageViewHolder
                holder.binding.itemImage.setOnClickListener {
                    onImageClickListener.onClickImage(item, holderIvImage.itemView)
                }
                holder.binding.favouriteImage.setOnClickListener {
                    onFavouriteClickListener.onClickFavourite(item, holder.binding.favouriteImage)
                }
                if (item.favourite){
                    holder.binding.favouriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
                }else{
                    holder.binding.favouriteImage.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                holder.binding.executePendingBindings()
                holder.bind(item)
            } 1 -> {
                val holder = holderIvImage as IvVideoViewHolder
                holder.binding.bindedItem = item
                holder.binding.url = item.searchForMobileVideo()
                holder.binding.executePendingBindings()
                holder.bind(item)
            } else -> {
            val holder = holderIvImage as IvLoadingViewHolder
            holder.binding.executePendingBindings()
            holder.bind(item)
            }
        }
}

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.mediaType){
            "image" -> 0
            "video" -> 1
            else -> 2
        }
    }


    class IvVideoViewHolder(val binding: IvVideoItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(domainIvItem: DomainIvItem){
            binding.bindedItem = domainIvItem
            binding.executePendingBindings()
        }
    }

    class IvImageViewHolder(val binding: IvImageItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(domainIvItem: DomainIvItem){
            binding.bindedItem = domainIvItem
            binding.executePendingBindings()
        }
    }

    class IvLoadingViewHolder(val binding: IvLoadingItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(domainIvItem: DomainIvItem){
            binding.bindedItem = domainIvItem
            binding.executePendingBindings()
        }
    }



    class OnImageClickListener(val clickListener: (domainIvItem: DomainIvItem?, v: View) -> Unit){
        fun onClickImage(domainIvItem: DomainIvItem?, view: View) = clickListener(domainIvItem, view)
    }
    class OnFavouriteClickListener(val clickListener: (domainIvItem: DomainIvItem?, v: ImageView) -> Unit){
        fun onClickFavourite(domainIvItem: DomainIvItem?, view: ImageView) = clickListener(domainIvItem, view)
    }



}