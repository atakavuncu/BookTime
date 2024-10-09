package com.atakavuncu.booktime.di

import android.content.Context
import com.atakavuncu.booktime.data.api.OpenLibraryApi
import com.atakavuncu.booktime.utils.SessionManager
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    /*
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "database"
        ).build()
    }

    @Provides
    fun provideUserDao(database: Database) = database.userDao()

    @Provides
    fun provideReadBooksDao(database: Database) = database.readBooksDao()

    @Provides
    fun provideReadingBooksDao(database: Database) = database.readingBooksDao()

    @Provides
    fun provideStoppedBooksDao(database: Database) = database.stoppedBooksDao()

    @Provides
    fun provideNotStartedBooksDao(database: Database) = database.notStartedBooksDao()

    @Provides
    fun provideFavoritesDao(database: Database) = database.favoritesDao()

    @Provides
    fun provideListsDao(database: Database) = database.listsDao()
    */

    @Provides
    @Singleton
    fun provideOpenLibraryApi(): OpenLibraryApi = OpenLibraryApi.create()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}