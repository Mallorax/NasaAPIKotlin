package com.example.spaceinformer.ui.potd

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.nasapi.potd.Potd
import com.squareup.picasso.Picasso

@BindingAdapter("potdTitle")
fun bindPotdTitle(textView: TextView, data: Potd?){
    textView.text = data?.title
}

@BindingAdapter("potdExplanation")
fun bindPotdExplanation(textView: TextView, data: Potd?){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
    }
    textView.text = data?.explanation
}

@BindingAdapter("potdImage")
fun bindPotdImage(imageView: ImageView, data: Potd?){
    Picasso.get().load(data?.url).into(imageView)
}