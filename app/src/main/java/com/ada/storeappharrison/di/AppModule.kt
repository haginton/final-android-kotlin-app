package com.ada.storeappharrison.di

import android.content.Context
import androidx.room.Room
import com.ada.storeappharrison.network.JWTInterceptor
import com.ada.storeappharrison.network.service.AuthService
import com.ada.storeappharrison.network.service.ProductsService
import com.ada.storeappharrison.storage.room.AppDatabase
import com.ada.storeappharrison.storage.room.dao.ProductDao
import com.ada.storeappharrison.storage.sharedpreferences.SharedPreferencesStorage
import com.ada.storeappharrison.storage.sharedpreferences.StorageToken
import com.ada.storeappharrison.utils.SHARED_PREFERENCES_FILE_NAME
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    fun provideProductDao( appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase{
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    fun provideStorageToken(@ApplicationContext appContext: Context): StorageToken{
        val sharedPreferences = appContext.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE)
        return SharedPreferencesStorage(sharedPreferences)
    }

    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun providesRetrofit(storageToken: StorageToken): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(JWTInterceptor(storageToken))
            .build()

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
            .create()

        return Retrofit.Builder()
            //.baseUrl("https://www.omdbapi.com/")
            .baseUrl("https://api-rest-java-production-bd09.up.railway.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}