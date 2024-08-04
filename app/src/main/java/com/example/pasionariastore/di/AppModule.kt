package com.example.pasionariastore.di

import android.content.Context
import androidx.room.Room
import com.example.pasionariastore.data.api.ApiBackend
import com.example.pasionariastore.repository.BackendRepositoryImpl
import com.example.pasionariastore.repository.CartRepository
import com.example.pasionariastore.repository.CartRepositoryImpl
import com.example.pasionariastore.repository.ProductRepository
import com.example.pasionariastore.repository.ProductRepositoryImpl
import com.example.pasionariastore.room.CartDatabaseDao
import com.example.pasionariastore.room.PasionariaDatabase
import com.example.pasionariastore.room.ProductDatabaseDao
import com.example.pasionariastore.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(Constants.API_BASE).addConverterFactory(
            GsonConverterFactory.create()
        ).build()

    @Singleton
    @Provides
    fun providesApiBackend(retrofit: Retrofit): ApiBackend = retrofit.create(ApiBackend::class.java)

    @Provides
    @Singleton
    fun providesBackendRepository(apiBackend: ApiBackend): BackendRepositoryImpl {
        return BackendRepositoryImpl(apiBackend)
    }
}