package com.example.spaceinformer.hilt

import android.content.Context
import com.example.spaceinformer.ui.imagesandvideos.LocalCacheDataSourceFactory
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExoPlayerModule {

    @Provides
    @Singleton
    @Named("SimpleCache")
    fun provideSimpleCache(@ApplicationContext context: Context): SimpleCache {
        return SimpleCache(
            File(context.cacheDir, "media"),
            LeastRecentlyUsedCacheEvictor(LocalCacheDataSourceFactory.MAX_CACHE_SIZE),
            ExoDatabaseProvider(context)
        )
    }

    @Provides
    @Singleton
    fun provideRenderersFactory(@ApplicationContext context: Context): DefaultRenderersFactory {
        return DefaultRenderersFactory(context)
    }

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context,
                         renderersFactory: DefaultRenderersFactory
    ): SimpleExoPlayer {
        return  SimpleExoPlayer.Builder(context, renderersFactory)
            .setTrackSelector(DefaultTrackSelector(context))
            .setLoadControl(DefaultLoadControl())
            .build()
    }

    @Provides
    @Singleton
    fun provideDataSourceFactory(@ApplicationContext context: Context): LocalCacheDataSourceFactory{
        return LocalCacheDataSourceFactory(context)
    }
}