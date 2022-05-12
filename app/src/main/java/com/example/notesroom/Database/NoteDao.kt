package com.example.notesroom.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesroom.Model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note: Note):Long

    @Query("SELECT * FROM roomNote")
    fun getNotes():List<Note>

    @Query("DELETE FROM roomNote WHERE id=:postion")
    fun removeNote(postion:Int)
}