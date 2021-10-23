package com.example.spaceinformer.ui.imagesandvideos

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.model.nasapi.imagesandpictures.IvItem
import com.squareup.picasso.Picasso

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, ivItem: IvItem?){
    if (ivItem != null){
        textView.text = ivItem.data?.first()?.title
    }else{
        Log.v("TEST", "test")
    }
}

@BindingAdapter("ivThumbnail")
fun bindIvThumbnail(imageView: ImageView, item: IvItem?){
    Picasso.get().load(item?.imageLinks?.first()?.href).into(imageView) //TODO: Size is hardcoded and thus it is good only for mobile phones (too small for tablets)
}