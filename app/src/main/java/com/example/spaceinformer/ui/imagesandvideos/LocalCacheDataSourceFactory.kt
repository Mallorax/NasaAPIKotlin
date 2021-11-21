package com.example.spaceinformer.ui.imagesandvideos

import android.content.Context
import android.net.Uri

import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.CacheDataSink
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


class LocalCacheDataSourceFactory(private val context: Context): DataSource.Factory {

    private val defaultDataSourceFactory: DefaultDataSourceFactory
    private val simpleCache: SimpleCache
    private val cacheDataSink: CacheDataSink
    private val fileDataSource: FileDataSource = FileDataSource()

    init {
        val entryPoint = EntryPointAccessors.fromApplication(context.applicationContext,
        LocalCacheDSFactoryEntryPoint::class.java)
        simpleCache = entryPoint.provideSimpleCache()
        cacheDataSink = CacheDataSink(simpleCache, MAX_FILE_SIZE)
        val userAgent = "Demo"
        val bandwidthMeter = DefaultBandwidthMeter.Builder(context).build()
        defaultDataSourceFactory = DefaultDataSourceFactory(context,
            bandwidthMeter,
            DefaultHttpDataSource.Factory().setUserAgent(userAgent))

    }

    override fun createDataSource(): DataSource {
        return CacheDataSource(
            simpleCache, defaultDataSourceFactory.createDataSource(),
            fileDataSource, cacheDataSink,
            CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null
        )
    }

    companion object {
        const val MAX_CACHE_SIZE = (100 * 1024 * 1024).toLong()
        const val MAX_FILE_SIZE = (5 * 1024 * 1024).toLong()
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface LocalCacheDSFactoryEntryPoint {
        @Named("SimpleCache")
        fun provideSimpleCache(): SimpleCache
    }
}