package com.genius.wasylews.myteviant.di

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.genius.wasylews.myteviant.BuildConfig
import com.genius.wasylews.myteviant.common.network.ApiResultCallAdapterFactory
import com.genius.wasylews.myteviant.common.network.ApiResultResolver
import com.genius.wasylews.myteviant.common.network.Connectivity
import com.genius.wasylews.myteviant.sdk.TeviantApi
import com.genius.wasylews.myteviant.sdk.TeviantRepository
import com.genius.wasylews.myteviant.sdk.remote.MyTeviantApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://my.teviant.ua/"

    @Singleton
    @Provides
    fun provideCookieJar(@ApplicationContext context: Context): CookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(context)
    )

    @Singleton
    @Provides
    fun provideOkHttpClient(cookieJar: CookieJar): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cookieJar(cookieJar)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideApiResultResolver(
        @ApplicationContext context: Context,
        connectivity: Connectivity
    ) = ApiResultResolver(connectivity, context)

    @Singleton
    @Provides
    fun provideApiResultCallAdapterFactory(apiResultResolver: ApiResultResolver) =
        ApiResultCallAdapterFactory(apiResultResolver)

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(JspoonConverterFactory.create())
        .addCallAdapterFactory(apiResultCallAdapterFactory)
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideMyTeviantApi(retrofit: Retrofit): MyTeviantApi =
        retrofit.create(MyTeviantApi::class.java)

    @Provides
    fun provideTeviantApi(api: MyTeviantApi): TeviantApi = TeviantRepository(
        api
    )
}