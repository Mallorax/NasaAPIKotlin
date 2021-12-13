package com.example.spaceinformer.ui.imagesandvideos

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.squareup.picasso.Picasso

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, item: DomainIvItem){
    textView.text = item.title
}

@BindingAdapter("ivThumbnail")
fun bindIvThumbnail(imageView: ImageView, item: DomainIvItem){
    if (item.imageThumbnail.isNotEmpty()) {
        Picasso.get().load(item.imageThumbnail).into(imageView)
    }
}