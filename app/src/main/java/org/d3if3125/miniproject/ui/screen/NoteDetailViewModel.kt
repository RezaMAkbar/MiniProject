package org.d3if3125.miniproject.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3125.miniproject.database.NoteDao
import org.d3if3125.miniproject.model.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteDetailViewModel (private val dao: NoteDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(title: String, content: String, categoryNote: String) {
        val noteStatus = if (categoryNote == "2") "2" else "0"
        val note = Note(
            date = formatter.format(Date()),
            title = title,
            content = content,
            category = categoryNote,
            status = noteStatus
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(note)
        }
    }

    suspend fun getNote(id: Long): Note? {
        return dao.getNoteById(id)
    }

    fun update(id: Long, title: String, content: String, categoryNote: String, noteStatus: String) {
        val note = Note(
            id      = id,
            date = formatter.format(Date()),
            title = title,
            content = content,
            category = categoryNote,
            status = noteStatus
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(note)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}