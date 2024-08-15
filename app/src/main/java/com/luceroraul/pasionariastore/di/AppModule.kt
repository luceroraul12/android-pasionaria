package com.luceroraul.pasionariastore.di

import android.content.Context
import androidx.room.Room
import com.luceroraul.pasionariastore.BuildConfig
import com.luceroraul.pasionariastore.converter.LongToDateAdapter
import com.luceroraul.pasionariastore.data.CustomDataStore
import com.luceroraul.pasionariastore.data.api.ApiBackend
import com.luceroraul.pasionariastore.interceptor.BackendInterceptor
import com.luceroraul.pasionariastore.interceptor.ErrorInterceptor
import com.luceroraul.pasionariastore.repository.BackendRepository
import com.luceroraul.pasionariastore.repository.BackendRepositoryImpl
import com.luceroraul.pasionariastore.repository.CartRepository
import com.luceroraul.pasionariastore.repository.CartRepositoryImpl
import com.luceroraul.pasionariastore.repository.ProductRepository
import com.luceroraul.pasionariastore.repository.ProductRepositoryImpl
import com.luceroraul.pasionariastore.room.CartDatabaseDao
import com.luceroraul.pasionariastore.room.PasionariaDatabase
import com.luceroraul.pasionariastore.room.ProductDatabaseDao
import com.luceroraul.pasionariastore.util.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCartRepository(cartDao: CartDatabaseDao): CartRepository {
        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun providesProductRepository(productDao: ProductDatabaseDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

    @Singleton
    @Provides
    fun providesProductDao(productDatabase: PasionariaDatabase): ProductDatabaseDao {
        return productDatabase.productDao()
    }

    @Singleton
    @Provides
    fun providesCartDao(productDatabase: PasionariaDatabase): CartDatabaseDao {
        return productDatabase.cartDao()
    }

    @Singleton
    @Provides
    fun providesPasionariaDatabase(@ApplicationContext context: Context): PasionariaDatabase {
        return Room.databaseBuilder(
            context = context, klass = PasionariaDatabase::class.java, name = "pasionaria_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesCustomDatastore(@ApplicationContext context: Context): CustomDataStore {
        return CustomDataStore(context)
    }

    @Singleton
    @Provides
    fun providesBackendInterceptor(
        customDataStore: CustomDataStore,
    ): BackendInterceptor {
        return BackendInterceptor(
            customDataStore = customDataStore,
        )
    }

    @Singleton
    @Provides
    fun providesErrorInterceptor(): ErrorInterceptor = ErrorInterceptor()

    @Singleton
    @Provides
    fun providesRetrofit(
        backendInterceptor: BackendInterceptor,
        errorInterceptor: ErrorInterceptor
    ): Retrofit {
        // Para interceptors
        val client =
            OkHttpClient.Builder()
                .addInterceptor(backendInterceptor)
                .addInterceptor(errorInterceptor)
                .build()
        // Para converter
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, LongToDateAdapter())
            .create()
        return Retrofit.Builder().baseUrl(BuildConfig.API_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client).build()
    }

    @Singleton
    @Provides
    fun providesApiBackend(retrofit: Retrofit): ApiBackend = retrofit.create(ApiBackend::class.java)

    @Provides
    @Singleton
    fun providesBackendRepository(apiBackend: ApiBackend): BackendRepository {
        return BackendRepositoryImpl(apiBackend)
    }
}