package com.genius.wasylews.myteviant.di

import android.content.Context
import com.genius.wasylews.myteviant.common.network.Connectivity
import com.genius.wasylews.myteviant.common.network.ConnectivityImpl
import com.genius.wasylews.myteviant.sdk.TeviantApi
import com.genius.wasylews.myteviant.sdk.TeviantRepository
import com.genius.wasylews.myteviant.sdk.local.LocalStorage
import com.genius.wasylews.myteviant.sdk.remote.AuthApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, LocalStorageModule::class])
@InstallIn(SingletonComponent::class)
internal object AppModule {

   @Singleton
   @Provides
   fun provideConnectivity(@ApplicationContext context: Context): Connectivity =
      ConnectivityImpl(context)

   @Provides
   fun provideTeviantApi(authApi: AuthApi,
                         localStorage: LocalStorage): TeviantApi = TeviantRepository(
      authApi,
      localStorage
   )
}