package com.example.spaceinformer.ui.imagesandvideos

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.squareup.picasso.Picasso

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, item: DomainIvItem){
    textView.text = item.title
}

@BindingAdapter("ivThumbnail")
fun bindIvThumbnail(imageView: ImageView, item: DomainIvItem){
    Picasso.get().load(item.imageThumbnail).into(imageView) //TODO: Size is hardcoded and thus it is good only for mobile phones (too small for tablets)
}