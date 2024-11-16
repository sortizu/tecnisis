package com.example.tecnisis.data.artist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtistaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(artista: Artista)

    @Query("SELECT * FROM artista WHERE id_artista = :idArtista")
    suspend fun getArtistaById(idArtista: Int): Artista?

    @Query("DELETE FROM artista WHERE id_artista = :idArtista")
    suspend fun deleteArtistaById(idArtista: Int)

    @Query("SELECT * FROM artista")
    suspend fun getAllArtistas(): List<Artista>
}
