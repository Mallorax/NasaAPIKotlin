package com.example.spaceinformer.ui.imagesandvideos

import com.google.android.exoplayer2.Player

interface PlayerStateChange {

    fun onVideoDurationRetrieved(duration: Long, player: Player)

    fun onVideoBuffering(player: Player)

    fun onStartedPlaying(player: Player)

    fun onFinishedPlaying(player: Player)
}