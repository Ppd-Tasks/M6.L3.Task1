package com.example.notesroom.Manager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesroom.Database.NoteDao
import com.example.notesroom.Model.Note

@Database(entities = [Note::class], version = 1)
abstract class RoomManager : RoomDatabase() {
    abstract fun noteDao(): NoteDao?

    companion object{

        @Volatile
        private var INSTANCE:RoomManager?=null
        fun getDatabase(context: Context):RoomManager{
            synchronized(this){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,RoomManager::class.java,"note_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}