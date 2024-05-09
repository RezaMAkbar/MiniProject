package org.d3if3125.miniproject.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3125.miniproject.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("SELECT * FROM note ORDER BY date DESC")
    fun getNoteDescDate(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY date ASC")
    fun getNoteAscDate(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title DESC")
    fun getNoteDescTitle(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY title ASC")
    fun getNoteAscTitle(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY category ASC")
    fun getNoteAscCate(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY category DESC")
    fun getNoteDescCate(): Flow<List<Note>>

    @Query("SELECT * FROM note where category = '2' ORDER BY status DESC")
    fun getNoteStat(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Long)


}