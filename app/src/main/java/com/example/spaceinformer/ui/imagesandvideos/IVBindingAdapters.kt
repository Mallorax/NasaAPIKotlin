package com.example.spaceinformer.ui.imagesandvideos

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.nasapi.imagesandpictures.IvItem

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, ivItem: IvItem?){
    if (ivItem != null){
        textView.text = ivItem.data?.first()?.title
    }else{
        Log.v("TEST", "test")
    }
}