package com.example.spaceinformer.ui.imagesandvideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.databinding.IvListItemBinding
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem


class IVListAdapter(private val onClickListener: OnClickListener):
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
        holderIvList.itemView.setOnClickListener{
            onClickListener.onClick(item, holderIvList.itemView)
        }
        holderIvList.bind(item)

    }

    class IvListViewHolder(private var binding: IvListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(ivItem: IvItem){
            binding.bindedItem = ivItem
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (ivItem: IvItem?, v: View) -> Unit){
        fun onClick(ivItem: IvItem?, view: View) = clickListener(ivItem, view)
    }


}