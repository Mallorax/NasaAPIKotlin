package com.example.spaceinformer.ui.imagesandvideos


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

@BindingAdapter("ivItemTitle")
fun bindIvTitle(textView: TextView, item: DomainIvItem){
    textView.text = item.title
}

@BindingAdapter("ivThumbnail")
fun bindIvThumbnail(imageView: ImageView, item: DomainIvItem){
    if (item.imageThumbnail.isNotEmpty()) {
        Picasso.get().load(item.imageThumbnail).into(imageView)
    }
     //TODO: Size is hardcoded and thus it is good only for mobile phones (too small for tablets)
}

@BindingAdapter("video_url", "on_state_change", "imageLink")
fun PlayerView.loadVideo(url: String?, change: PlayerStateChange, imageLink: String){
    val context = context.applicationContext
    if (url == null || url.isEmpty()) return
    val renderersFactory = DefaultRenderersFactory(context)
    val player = SimpleExoPlayer.Builder(context, renderersFactory)
        .setTrackSelector(DefaultTrackSelector(context))
        .setLoadControl(DefaultLoadControl())
        .build()

    player.playWhenReady = true
    player.repeatMode = Player.REPEAT_MODE_ALL
    setKeepContentOnPlayerReset(true)
    this.useController = true

    val mediaItem = MediaItem.fromUri(url.replace("http", "https"))

    val mediaSource = ProgressiveMediaSource.Factory(
        LocalCacheDataSourceFactory(context)
    ).createMediaSource(mediaItem)

    player.setMediaSource(mediaSource)
    player.videoDecoderCounters
    player.prepare()

    this.player = player

    Picasso.get().load(imageLink).into(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            val drawable = BitmapDrawable(resources, bitmap)
            this@loadVideo.defaultArtwork = drawable
        }

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            this@loadVideo.defaultArtwork = errorDrawable
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            this@loadVideo.defaultArtwork = placeHolderDrawable
        }
    })
    this.player!!.addListener(object : Player.Listener{
        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            Toast.makeText(this@loadVideo.context, "Error while playing media", Toast.LENGTH_LONG).show()
        }


        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)

            if (playbackState == Player.STATE_READY){
                change.onVideoDurationRetrieved((this@loadVideo.player as SimpleExoPlayer).duration, player)
            }
            if (playbackState == Player.STATE_BUFFERING){
                change.onVideoBuffering(player)
            }
            if (playbackState == Player.STATE_READY && player.playWhenReady){
                change.onStartedPlaying(player)
            }
        }
    })
}