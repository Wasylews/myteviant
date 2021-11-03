package com.genius.wasylews.myteviant.di

import android.content.Context
import com.genius.wasylews.myteviant.common.network.Connectivity
import com.genius.wasylews.myteviant.common.network.ConnectivityImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
internal object AppModule {

   @Provides
   @Singleton
   fun provideConnectivity(@ApplicationContext context: Context): Connectivity =
      ConnectivityImpl(context)
}