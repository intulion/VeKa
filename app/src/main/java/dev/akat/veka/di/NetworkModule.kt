package dev.akat.veka.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.akat.veka.BuildConfig
import dev.akat.veka.api.ApiService
import dev.akat.veka.api.AuthManager
import dev.akat.veka.api.NetworkChecker
import dev.akat.veka.data.AppPreferences
import dev.akat.veka.data.exceptions.NoNetworkException
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthManager(preferences: AppPreferences): AuthManager =
        AuthManager(preferences)

    @Provides
    @Singleton
    fun provideNetworkChecker(context: Context): NetworkChecker =
        NetworkChecker(context)

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkHttp(authManager: AuthManager, networkChecker: NetworkChecker): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("access_token", authManager.credentials.token)
                    .addQueryParameter("v", BuildConfig.API_VERSION)
                    .build()

                val request = chain.request().newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                if (!networkChecker.isConnected) {
                    throw NoNetworkException("No network connection")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
}
