package com.example.spaceinformer.ui.imagesandvideos

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.nasapi.imagesandpictures.Item

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, item: Item?){
    if (item != null){
        textView.text = item.data?.first()?.title
    }else{
        Log.v("TEST", "test")
    }
}