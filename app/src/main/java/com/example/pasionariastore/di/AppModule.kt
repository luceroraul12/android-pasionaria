package com.example.pasionariastore.di

import android.content.Context
import androidx.room.Room
import com.example.pasionariastore.converter.LongToDateAdapter
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.interceptor.BackendInterceptor
import com.example.pasionariastore.repository.BackendRepository
import com.example.pasionariastore.repository.BackendRepositoryImpl
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.CartRepositoryImpl
import com.example.pasionariastore.repository.ProductRepository
import com.example.pasionariastore.repository.ProductRepositoryImpl
import com.example.pasionariastore.room.CartDatabaseDao
import com.example.pasionariastore.room.PasionariaDatabase
import com.example.pasionariastore.room.ProductDatabaseDao
import com.example.pasionariastore.util.Constants
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
    fun providesRetrofit(): Retrofit {
        // Para interceptors
        val client = OkHttpClient.Builder().addInterceptor(BackendInterceptor()).build()
        // Para converter
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, LongToDateAdapter())
            .create()
        return Retrofit.Builder().baseUrl(Constants.API_BASE)
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