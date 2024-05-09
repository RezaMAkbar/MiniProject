package org.d3if3125.miniproject.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3125.miniproject.database.NoteDao
import org.d3if3125.miniproject.model.Note

class NoteMainViewModel (dao: NoteDao) : ViewModel() {
    val dateAsc: StateFlow<List<Note>> = dao.getNoteAscDate().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val dateDesc: StateFlow<List<Note>> = dao.getNoteDescDate().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val titleAsc: StateFlow<List<Note>> = dao.getNoteAscTitle().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val titleDesc: StateFlow<List<Note>> = dao.getNoteDescTitle().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val cateAsc: StateFlow<List<Note>> = dao.getNoteAscCate().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val cateDesc: StateFlow<List<Note>> = dao.getNoteDescCate().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    val stat: StateFlow<List<Note>> = dao.getNoteStat().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}