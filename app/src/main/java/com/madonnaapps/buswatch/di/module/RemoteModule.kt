package com.madonnaapps.buswatch.di.module

import com.madonnaapps.buswatch.BuildConfig
import com.madonnaapps.buswatch.data.datastore.prediction.PredictionRemoteDataStore
import com.madonnaapps.buswatch.domain.model.Prediction
import com.madonnaapps.buswatch.remote.datastore.PredictionRemoteDataStoreImpl
import com.madonnaapps.buswatch.remote.mapper.RemoteMapper
import com.madonnaapps.buswatch.remote.mapper.RemotePredictionMapper
import com.madonnaapps.buswatch.remote.model.prediction.RemotePrediction
import com.madonnaapps.buswatch.remote.service.TrueTimeApiService
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@Module
abstract class RemoteModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideSimpleDataFormat(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMdd HH:mm", Locale.US)
        }

        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    // Inject api key and json format as query parameters
                    val url = chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", BuildConfig.TRUE_TIME_API_KEY)
                        .addQueryParameter("format", "json")
                        .build()
                    val request = chain.request()
                        .newBuilder()
                        .url(url)
                        .build()
                    return@addInterceptor chain.proceed(request)
                }
                .build()
        }

        @Provides
        @JvmStatic
        fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.TRUE_TIME_API_BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        @Provides
        @JvmStatic
        fun provideTrueTimeApiService(retrofit: Retrofit): TrueTimeApiService {
            return retrofit.create(TrueTimeApiService::class.java)
        }
    }

    @Binds
    abstract fun bindPredictionRemoteDataStore(
        dataStore: PredictionRemoteDataStoreImpl
    ): PredictionRemoteDataStore

    @Binds
    abstract fun bindRemotePredictionMapper(
        mapper: RemotePredictionMapper
    ): RemoteMapper<RemotePrediction, Prediction?>
}