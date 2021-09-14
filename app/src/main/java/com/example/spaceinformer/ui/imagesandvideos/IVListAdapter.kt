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
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem


class IVListAdapter(private val onImageClickListener: OnImageClickListener,
                    private val onFavouriteClickListener: OnFavouriteClickListener):
    ListAdapter<IvItem, IVListAdapter.IvListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<IvItem>(){
        override fun areItemsTheSame(oldIvItem: IvItem, newIvItem: IvItem): Boolean {
            return oldIvItem === newIvItem
        }

        override fun areContentsTheSame(oldIvItem: IvItem, newIvItem: IvItem): Boolean {
            return oldIvItem.data?.get(0)?.nasaId == newIvItem.data?.get(0)?.nasaId
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
        if (item?.data?.first()?.favourite == true){
            holderIvList.binding.favouriteImage.setImageResource(R.drawable.ic_baseline_favorite_24)
        }
        holderIvList.bind(item)

    }

    class IvListViewHolder(val binding: IvListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ivItem: IvItem){
            binding.bindedItem = ivItem
            binding.executePendingBindings()
        }
    }

    class OnImageClickListener(val clickListener: (ivItem: IvItem?, v: View) -> Unit){
        fun onClickImage(ivItem: IvItem?, view: View) = clickListener(ivItem, view)
    }
    class OnFavouriteClickListener(val clickListener: (ivItem: IvItem?, v: ImageView) -> Unit){
        fun onClickFavourite(ivItem: IvItem?, view: ImageView) = clickListener(ivItem, view)
    }


}