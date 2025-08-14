package com.gongbaek.android.di

import android.content.Context
import android.content.SharedPreferences
import com.gongbaek.android.di.qualifier.LectureTimetablePrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @LectureTimetablePrefs
    @Provides
    @Singleton
    fun provideLectureTimetableSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("lecture_timetable_prefs", Context.MODE_PRIVATE)
}
