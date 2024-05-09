package org.d3if3125.miniproject.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3125.miniproject.database.NoteDao
import org.d3if3125.miniproject.ui.screen.NoteDetailViewModel
import org.d3if3125.miniproject.ui.screen.NoteMainViewModel

class ViewModelFactory(private val dao: NoteDao) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteMainViewModel::class.java)) {
            return NoteMainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
            return NoteDetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}