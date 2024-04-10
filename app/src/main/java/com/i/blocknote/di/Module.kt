package com.i.blocknote.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.i.blocknote.db.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun getDatBase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = NoteDataBase::class.java,
            name = "note_database"
        ).build()
    }
}