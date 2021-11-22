package com.example.spaceinformer.ui.imagesandvideos

import android.content.Context
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
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerControlView


class IVListAdapter(private val onImageClickListener: OnImageClickListener,
                    private val onFavouriteClickListener: OnFavouriteClickListener,
                    private val onFocusListener: OnFocusListener,
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
                bindImageViewHolder(holder, item)
            } 1 -> {
                val holder = holderIvImage as IvVideoViewHolder
                bindVideoViewHolder(holder, item)
            } else -> {
            val holder = holderIvImage as IvLoadingViewHolder
            holder.binding.executePendingBindings()
            holder.bind(item)
            }
        }
}
    private fun bindVideoViewHolder(holder: IvVideoViewHolder, item: DomainIvItem){
        holder.binding.parent.setOnFocusChangeListener { _, hasFocus ->
            onFocusListener.onFocus(holder.binding.itemVideoExoplayer, hasFocus, MediaItem.fromUri(item.searchForMobileVideo()!!.replace("http", "https")))
        }
        holder.binding.parent.setOnClickListener {
            it.requestFocus()
        }
        holder.bind(item)
    }

    private fun bindImageViewHolder(holder: IvImageViewHolder, item: DomainIvItem){
        holder.binding.itemImage.setOnClickListener {
            onImageClickListener.onClickImage(item, holder.itemView)
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
    }



    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item.mediaType){
            "image" -> 0
            "video" -> 1
            else -> 2
        }
    }

    private fun createMediaSource(item: DomainIvItem): ProgressiveMediaSource{
        val mediaItem = MediaItem.fromUri(item.searchForMobileVideo()!!.replace("http", "https"))
        return ProgressiveMediaSource.
        Factory(LocalCacheDataSourceFactory(context.applicationContext))
            .createMediaSource(mediaItem)
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
    class OnFocusListener(val focusListener: (playerView: StyledPlayerControlView, hasFocus: Boolean, mediaSource: MediaItem) -> Unit){
        fun onFocus(playerView: StyledPlayerControlView, hasFocus: Boolean, mediaSource: MediaItem) = focusListener(playerView, hasFocus, mediaSource)
    }




}