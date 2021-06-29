package com.example.spaceinformer.UI.ImagesAndVideos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R

import com.example.spaceinformer.model.NasaAPI.get.Item

class IVListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<Item, IVListAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.data[0].nasa_id == newItem.data[0].nasa_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.iv_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.textView.text = item.data[0].title
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item, holder.itemView)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.iv_list_item_title)
    }

    class OnClickListener(val clickListener: (item: Item?, v: View) -> Unit){
        fun onClick(item: Item?, view: View) = clickListener(item, view)
    }


}