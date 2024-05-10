package com.hutapp.org.notes.hut.android.di

import android.content.Context
import androidx.room.Room
import com.hutapp.org.notes.hut.android.activity.FloatingActivityForGetShered.SharedIsNewEntityIsExistImpl
import com.hutapp.org.notes.hut.android.db.NoteDataBase
import com.hutapp.org.notes.hut.android.utilsAccount.MyGoogleSignIn
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

    @Provides
    fun getMyGoogleSignIn(@ApplicationContext context: Context): MyGoogleSignIn {
      return  MyGoogleSignIn(context = context)
    }


    @Provides
    fun getSharedPref(@ApplicationContext context: Context) =
        context.getSharedPreferences(SharedIsNewEntityIsExistImpl.SHARED_NAME, Context.MODE_PRIVATE)
}