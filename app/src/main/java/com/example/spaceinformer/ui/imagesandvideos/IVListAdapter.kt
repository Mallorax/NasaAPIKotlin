package com.example.spaceinformer.ui.imagesandvideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.databinding.IvListItemBinding
import com.example.spaceinformer.nasapi.tmp.Item


class IVListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Item, IVListAdapter.IvListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data?.get(0)?.nasaId == newItem.data?.get(0)?.nasaId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IvListItemBinding.inflate(inflater, parent, false)
        return IvListViewHolder(binding)
    }

    override fun onBindViewHolder(holderIvList: IvListViewHolder, position: Int) {
        val item = getItem(position)
        holderIvList.itemView.setOnClickListener{
            onClickListener.onClick(item, holderIvList.itemView)
        }
        holderIvList.bind(item)

    }

    class IvListViewHolder(private var binding: IvListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Item){
            binding.bindedItem = item
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (item: Item?, v: View) -> Unit){
        fun onClick(item: Item?, view: View) = clickListener(item, view)
    }


}