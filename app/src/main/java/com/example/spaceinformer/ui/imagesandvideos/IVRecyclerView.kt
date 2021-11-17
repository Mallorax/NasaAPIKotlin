package com.example.spaceinformer.ui.imagesandvideos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.Display
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spaceinformer.R
import com.example.spaceinformer.model.appmodels.DomainIvItem
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso

class IVRecyclerView(context: Context, attributeSet: AttributeSet)
    : RecyclerView(context, attributeSet) {


    private var thumbnail: ImageView? = null
    private lateinit var volumeControl: ImageView
    private var viewHolderParent: View? = null
    private val videoSurfaceView: PlayerView?
    private var videoPlayer: SimpleExoPlayer?
    private var progressBar: ProgressBar? = null
    private lateinit var frameLayout: FrameLayout

    private enum class VolumeState {ON, OFF}
    private var videoList: List<DomainIvItem> = mutableListOf()
    private var videoSurfaceDefaultHeight = 0
    private var screenDefaultHeight = 0
    private var playPosition = -1
    private var isVideoViewAdded: Boolean = false
    private var appContext: Context = this.context.applicationContext

    private var volumeState: VolumeState

    init {
        val display: Display?
        val windowManager = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R){

            val windowMetrics = windowManager.currentWindowMetrics
            videoSurfaceDefaultHeight = windowMetrics.bounds.width()
            screenDefaultHeight = windowMetrics.bounds.height()
        }else{
            @Suppress("DEPRECATION")
            display = windowManager.defaultDisplay
            val point = Point()
            @Suppress("DEPRECATION")
            display.getSize(point)
            videoSurfaceDefaultHeight = point.x
            screenDefaultHeight = point.y
        }

        videoSurfaceView = PlayerView(appContext)
        videoSurfaceView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

        val bandwidthMeter = DefaultBandwidthMeter.Builder(appContext).build()
        val trackSelector = DefaultTrackSelector(context)

        videoPlayer = SimpleExoPlayer.Builder(appContext)
            .setTrackSelector(trackSelector)
            .setBandwidthMeter(bandwidthMeter)
            .build()

        videoSurfaceView.useController = false
        videoSurfaceView.player = videoPlayer
        volumeState = VolumeState.ON

        addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == SCROLL_STATE_IDLE){
                    if (thumbnail != null){
                        thumbnail!!.visibility = VISIBLE
                    }

                    if (!recyclerView.canScrollVertically(1)){
                        playVideo(true)
                    }else{
                        playVideo(false)
                    }
                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener{
            override fun onChildViewAttachedToWindow(view: View) {}

            override fun onChildViewDetachedFromWindow(view: View) {
                if (viewHolderParent != null && viewHolderParent!!.equals(view)){
                    resetVideoView()
                }
            }
        })

        videoPlayer!!.addListener(object : Player.Listener{
            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                super.onTimelineChanged(timeline, reason)
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray,
                trackSelections: TrackSelectionArray
            ) {
                super.onTracksChanged(trackGroups, trackSelections)
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
            }

            @SuppressLint("SwitchIntDef")
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                when(reason){
                    Player.STATE_BUFFERING -> {
                        if (progressBar != null){
                            progressBar!!.visibility = VISIBLE
                        }
                    }
                    Player.STATE_ENDED -> videoPlayer!!.seekTo(0)
                    Player.STATE_IDLE -> {}
                    Player.STATE_READY -> {
                        if (progressBar != null){
                            progressBar!!.visibility = VISIBLE
                        }
                        if (!isVideoViewAdded){
                            addVideoView()
                        }
                    }else -> {}
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {}
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPositionDiscontinuity(
                oldPosition: Player.PositionInfo,
                newPosition: Player.PositionInfo,
                reason: Int
            ) {}

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {}
            override fun onSeekProcessed() {}
        })

    }

    fun playVideo(isEndOfList: Boolean){
        val targetPosition: Int
        if (!isEndOfList){
            val startPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            var endPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            if (endPosition - startPosition > 1){
                endPosition = startPosition + 1
            }

            if (startPosition < 0 || endPosition < 0){
                return
            }

            targetPosition = if (startPosition != endPosition){
                val startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition)
                val endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition)

                if(startPosition > endPosition) startPositionVideoHeight else endPositionVideoHeight

            }else{
                startPosition
            }
        }else{
            targetPosition = videoList.size - 1
        }

        if (targetPosition == playPosition){
            return
        }

        playPosition = targetPosition
        if (videoSurfaceView == null){
            return
        }

        videoSurfaceView.visibility = INVISIBLE
        removeVideoView(videoSurfaceView)

        val currentPosition = targetPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val child: View = getChildAt(currentPosition) ?: return

        val holder = child.tag as IVListAdapter.IvVideoViewHolder

        if (holder == null){
            playPosition = -1
            return
        }

        val binding = holder.binding
        thumbnail = binding.thumbnail
        progressBar = binding.progressBar
        volumeControl = binding.volumeControl
        viewHolderParent = holder.itemView
//        requestManager = holder.requestManager
        frameLayout = binding.mediaContainer

        videoSurfaceView.player = videoPlayer
        viewHolderParent!!.setOnClickListener(videoViewClickListener)

        val dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, "Space Informer")
        )
        val item = videoList[targetPosition]
        val mediaUrl = item.searchForMobileVideo() ?: return
        val mediaItem = MediaItem.Builder()
            .setUri(mediaUrl)
            .setMediaId(item.nasaId)
            .setTag(item.mediaType)
            .build()
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
        videoPlayer!!.setMediaSource(videoSource)
        videoPlayer!!.prepare()
        videoPlayer!!.playWhenReady = true
    }

    private val videoViewClickListener = OnClickListener{
        toggleVolume()
    }

    private fun getVisibleVideoSurfaceHeight(playPosition: Int): Int{
        val at = playPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val child = getChildAt(at) ?: return 0
        val location = IntArray(2)
        child.getLocationInWindow(location)
        return if (location[1] < 0){
            location[1] + videoSurfaceDefaultHeight
        }else{
            screenDefaultHeight - location[1]
        }
    }

    private fun removeVideoView(videoView: PlayerView){
        val parent = (videoView.parent as ViewGroup)
        val index = parent.indexOfChild(videoView)
        if (index >= 0){
            parent.removeViewAt(index)
            isVideoViewAdded = false
            viewHolderParent!!.setOnClickListener(null)
        }
    }

    private fun addVideoView(){
        frameLayout.addView(videoSurfaceView)
        isVideoViewAdded = true
        videoSurfaceView!!.requestFocus()
        videoSurfaceView.visibility = VISIBLE
        videoSurfaceView.alpha = 1.0F
        thumbnail!!.visibility = GONE
    }

    private fun resetVideoView(){
        if (!isVideoViewAdded){
            removeVideoView(videoSurfaceView!!)
            playPosition = -1
            videoSurfaceView.visibility = INVISIBLE
            thumbnail!!.visibility = VISIBLE

        }
    }

    fun releasePlayer(){
        if (videoPlayer != null){
            videoPlayer!!.release()
            videoPlayer = null
        }
        viewHolderParent = null
    }

    private fun toggleVolume(){
        if (videoPlayer != null){
            if (volumeState == VolumeState.OFF){
                setVolumeControl(VolumeState.ON)
            }else if(volumeState == VolumeState.ON){
                setVolumeControl(VolumeState.OFF)
            }
        }
    }

    private fun setVolumeControl(state: VolumeState){
        volumeState = state
        if (state == VolumeState.OFF){
            videoPlayer!!.volume = 0f
            animateVolumeControl()
        }else if(state == VolumeState.ON){
            videoPlayer!!.volume = 1f
            animateVolumeControl()
        }
    }

    private fun animateVolumeControl(){
        volumeControl.bringToFront()
        if (volumeState == VolumeState.OFF){
            Picasso.get().load(R.drawable.ic_baseline_volume_off_grey_24)
                .into(volumeControl)
        }else if ( volumeState == VolumeState.ON){
            Picasso.get().load(R.drawable.ic_baseline_volume_up_grey)
                .into(volumeControl)
        }
        volumeControl.animate().cancel()
        volumeControl.alpha = 1f
        volumeControl.animate()
            .alpha(0f)
            .setDuration(600)
            .setStartDelay(1000)
    }

    fun setVideoItemsList(videoItems: List<DomainIvItem>){
        this.videoList = videoItems
    }

}