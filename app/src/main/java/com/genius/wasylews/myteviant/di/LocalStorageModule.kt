package com.genius.wasylews.myteviant.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.genius.wasylews.myteviant.sdk.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object LocalStorageModule {

    private const val SHARED_PREFS_NAME = "myTeviant"
    private const val DB_NAME = "myTeviant.db"

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    ).build()
}