package com.example.notesroom.Helper

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.example.notesroom.Activity.MainActivity
import com.example.notesroom.Database.NoteRepository
import com.example.notesroom.Model.Note
import com.example.notesroom.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors

class AddDialog(var a: Activity) : Dialog(a) {
    private lateinit var et_note: EditText
    private lateinit var tv_add: TextView
    private lateinit var tv_cancel: TextView
    private lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_note)
        initViews()
        setCancelable(true)
    }

    private fun initViews() {
        et_note = findViewById(R.id.et_note)
        tv_add = findViewById(R.id.tv_add)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_cancel.setOnClickListener {
            et_note.setText("")
            dismiss()
        }
        tv_add.setOnClickListener {
            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy HH:MM:SS")
            var date = simpleDateFormat.format(Date())
            val note = Note(date, et_note.text.toString())
            noteExacutor(NoteRepository(a.application),note)
            dismiss()
        }
    }

    private fun noteExacutor(repository: NoteRepository, note:Note) {
        val exacutor= Executors.newSingleThreadExecutor()
        val handler= Handler(Looper.getMainLooper())
        exacutor.execute{
            repository.saveNote(note)
            var newNote=repository.getNotes().last()
            handler.post {
                MainActivity.notes.add(newNote)
                MainActivity.adapter.notifyDataSetChanged()
            }
        }


    }
}