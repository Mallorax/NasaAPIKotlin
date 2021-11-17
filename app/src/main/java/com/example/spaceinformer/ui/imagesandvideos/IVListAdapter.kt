package com.example.spaceinformer.ui.imagesandvideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.*
import com.example.spaceinformer.model.appmodels.DomainIvItem


class IVListAdapter(private val onImageClickListener: OnImageClickListener,
                    private val onFavouriteClickListener: OnFavouriteClickListener):
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
        when(viewType){
            0 -> {
                val binding = IvImageItemBindingImpl.inflate(inflater, parent, false)
                return IvImageViewHolder(binding)
            } 1 -> {
                val binding = IvLoadingItemBindingImpl.inflate(inflater, parent, false)
                return IvLoadingViewHolder(binding)
            } 2 -> {
                val binding = IvLoadingItemBindingImpl.inflate(inflater, parent, false)
                return IvLoadingViewHolder(binding)
            } 3 -> {
                val binding = IvLoadingItemBindingImpl.inflate(inflater, parent, false)
                return IvLoadingViewHolder(binding)
            } else -> {
                val binding = IvLoadingItemBindingImpl.inflate(inflater, parent, false)
                return IvLoadingViewHolder(binding)
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
                holder.bind(item)
            } 1 -> {

            }2 -> {
            val holder = holderIvImage as IvLoadingViewHolder
            holder.bind(item)
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.mediaType){
            "image" -> 0
            "video" -> 1
            "loading" -> 2
            else -> 3
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