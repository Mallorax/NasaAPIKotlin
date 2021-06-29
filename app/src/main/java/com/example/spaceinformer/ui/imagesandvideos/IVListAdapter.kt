package com.example.spaceinformer.ui.imagesandvideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.databinding.IvListItemBinding
import com.example.spaceinformer.nasapi.imagesandpictures.Data

import com.example.spaceinformer.nasapi.imagesandpictures.Item

class IVListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Item, IVListAdapter.IvListViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data[0].nasa_id == newItem.data[0].nasa_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IvListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = IvListItemBinding.inflate(inflater, parent, false)
        return IvListViewHolder(binding)
    }

    override fun onBindViewHolder(holderIvList: IvListViewHolder, position: Int) {
        val item = getItem(position)
        val data: Data = item.data[0]
        holderIvList.itemView.setOnClickListener{
            onClickListener.onClick(item, holderIvList.itemView)
        }
        holderIvList.bind(data)

    }

    class IvListViewHolder(private var binding: IvListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Data){
            binding.bindedData = data
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (item: Item?, v: View) -> Unit){
        fun onClick(item: Item?, view: View) = clickListener(item, view)
    }


}