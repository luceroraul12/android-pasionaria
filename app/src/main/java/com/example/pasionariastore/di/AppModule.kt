package com.example.pasionariastore.di

import android.content.Context
import androidx.room.Room
import com.example.pasionariastore.room.PasionariaDatabase
import com.example.pasionariastore.room.ProductDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun providesProductDao(productDatabase: PasionariaDatabase): ProductDatabaseDao {
        return productDatabase.productDao()
    }

    @Singleton
    @Provides
    fun providesPasionariaDatabase(@ApplicationContext context: Context): PasionariaDatabase {
        return Room.databaseBuilder(
            context = context, klass = PasionariaDatabase::class.java, name = "pasionaria_db"
        ).fallbackToDestructiveMigration().build()
    }
}