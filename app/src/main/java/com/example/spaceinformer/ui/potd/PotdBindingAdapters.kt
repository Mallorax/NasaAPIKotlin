package com.example.spaceinformer.ui.potd

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.model.nasapi.potd.Potd
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.squareup.picasso.Picasso

@BindingAdapter("potdTitle")
fun bindPotdTitle(textView: TextView, data: Potd?){
    if (data != null){
        textView.text = data.title
    }
}

@BindingAdapter("potdExplanation")
fun bindPotdExplanation(textView: TextView, data: Potd?){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
    }
    if (data != null){
        textView.text = data.explanation
    }

}

@BindingAdapter("potdImage")
fun bindPotdImage(imageView: ImageView, data: Potd?){
    if (data != null) {
        if (data.mediaType != "video") {
            imageView.visibility = View.VISIBLE
            Picasso.get().load(data.url).into(imageView)
        }else{
            imageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("potdVideo")
fun bindPotdVideo(videoView: StyledPlayerView, data: Potd?){
    if (data != null) {
        if (data.mediaType == "video") {
            videoView.visibility = View.VISIBLE
            val player = videoView.player
            val mediaItem = MediaItem.fromUri(data.url)
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.play()
        }else{
            videoView.visibility = View.GONE
        }
    }
}