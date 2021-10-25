package com.example.spaceinformer.ui.imagesandvideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListItemBinding
import com.example.spaceinformer.model.appmodels.DomainIvItem


class IVListAdapter(private val onImageClickListener: OnImageClickListener,
                    private val onFavouriteClickListener: OnFavouriteClickListener):
    ListAdapter<DomainIvItem, IVListAdapter.IvListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<DomainIvItem>(){
        override fun areItemsTheSame(oldDomainIvItem: DomainIvItem, newDomainIvItem: DomainIvItem): Boolean {
            return oldDomainIvItem === newDomainIvItem
        }

        override fun areContentsTheSame(oldDomainIvItem: DomainIvItem, newDomainIvItem: DomainIvItem): Boolean {
            return oldDomainIvItem.nasaId == newDomainIvItem.nasaId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IvListItemBinding.inflate(inflater, parent, false)
        return IvListViewHolder(binding)
    }

    override fun onBindViewHolder(holderIvList: IvListViewHolder, position: Int) {
        val item = getItem(position)
        holderIvList.binding.itemImage.setOnClickListener {
            onImageClickListener.onClickImage(item, holderIvList.itemView)
        }
        holderIvList.binding.favouriteImage.setOnClickListener {
            onFavouriteClickListener.onClickFavourite(item, holderIvList.binding.favouriteImage)
        }
        if (item.favourite){
            holderIvList.binding.favouriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        holderIvList.bind(item)

    }

    class IvListViewHolder(val binding: IvListItemBinding) : RecyclerView.ViewHolder(binding.root){
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