package com.gprifti.livetv.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gprifti.livetv.data.pref.PrefStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.gprifti.livetv.BuildConfig
import com.gprifti.livetv.data.api.APISearch
import com.gprifti.livetv.data.repository.Repository

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext appContext: Context) = appContext

    @Singleton
    @Provides
    fun providePreference(appContext: Context) = PrefStorage(appContext)

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideCharacterService(retrofit: Retrofit): APISearch = retrofit.create(APISearch::class.java)

    @Singleton
    @Provides
    fun provideRepository(prefStorage: PrefStorage, retrofit: APISearch) = Repository(prefStorage, retrofit)
}