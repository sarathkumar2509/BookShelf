package com.example.bookshelfexp.di

import android.content.Context
import androidx.room.Room
import com.example.bookshelfexp.db.BookDao
import com.example.bookshelfexp.db.BookDatabase
import com.example.bookshelfexp.main.DefaultMainRepository
import com.example.bookshelfexp.main.MainRepository
import com.example.bookshelfexp.models.api.BookApi
import com.example.bookshelfexp.util.Constants.Companion.BASE_URL
import com.example.bookshelfexp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookApi() : BookApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BookApi::class.java)


    @Singleton
    @Provides
    fun providesDatabase(db: BookDatabase) : BookDao{
        return db.getBookDao()
    }

    @Singleton
    @Provides
    fun provideMainRepository(api : BookApi , db : BookDatabase) : MainRepository = DefaultMainRepository(api,db)



    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }

    @Provides
    @Singleton
    fun provideBookDao(appDatabase: BookDatabase): BookDao {
        return appDatabase.getBookDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): BookDatabase {
        return Room.databaseBuilder(
                appContext,
                BookDatabase::class.java,
                "books.db"
        ).build()
    }

}