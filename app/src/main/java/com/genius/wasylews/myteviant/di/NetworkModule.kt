package com.genius.wasylews.myteviant.di

import com.genius.wasylews.myteviant.BuildConfig
import com.genius.wasylews.myteviant.common.network.ApiResultCallAdapterFactory
import com.genius.wasylews.myteviant.common.network.AuthCookieInterceptor
import com.genius.wasylews.myteviant.common.network.AuthInterceptor
import com.genius.wasylews.myteviant.sdk.remote.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "https://my.teviant.ua/"

    private fun makeOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    @Provides
    @Named("auth")
    fun provideAuthOkHttpClient(authCookieInterceptor: AuthCookieInterceptor) =
        makeOkHttpClient(authCookieInterceptor)

    @Provides
    fun provideOkHttpClient(authCookieInterceptor: AuthInterceptor) =
        makeOkHttpClient(authCookieInterceptor)

    @Provides
    @Named("auth")
    fun provideAuthRetrofit(
        @Named("auth") okHttpClient: OkHttpClient,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ) = makeRetrofit(okHttpClient, apiResultCallAdapterFactory)

    private fun makeRetrofit(
        okHttpClient: OkHttpClient,
        apiResultCallAdapterFactory: ApiResultCallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(JspoonConverterFactory.create())
        .addCallAdapterFactory(apiResultCallAdapterFactory)
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideAuthApi(@Named("auth") retrofit: Retrofit): AuthApi =
        retrofit.create(AuthApi::class.java)
}