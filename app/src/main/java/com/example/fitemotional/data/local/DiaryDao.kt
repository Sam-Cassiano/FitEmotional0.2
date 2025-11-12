package com.example.fitemotional.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fitemotional.data.model.DiaryEntry
import kotlinx.coroutines.flow.Flow

/**
 * DAO responsável pelas operações no banco de dados de entradas do diário.
 */
@Dao
interface DiaryDao {

    /**
     * Insere uma nova entrada no diário.
     * Caso já exista uma entrada com a mesma data, ela será substituída.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: DiaryEntry)

    /**
     * Atualiza uma entrada existente no diário.
     */
    @Update
    suspend fun update(entry: DiaryEntry)

    /**
     * Exclui uma entrada específica do diário.
     */
    @Delete
    suspend fun delete(entry: DiaryEntry)

    /**
     * Retorna todas as entradas salvas, ordenadas da mais recente para a mais antiga.
     */
    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntries(): Flow<List<DiaryEntry>>

    /**
     * Retorna todas as entradas de uma data específica.
     * Útil para estatísticas ou filtragem por dia.
     */
    @Query("SELECT * FROM entries WHERE date = :date ORDER BY date DESC")
    fun getEntriesByDate(date: String): Flow<List<DiaryEntry>>

    /**
     * Retorna a média da intensidade das emoções registradas.
     */
    @Query("SELECT AVG(intensity) FROM entries")
    fun getAverageIntensity(): Flow<Double?>

    /**
     * Retorna a contagem total de entradas no diário.
     */
    @Query("SELECT COUNT(*) FROM entries")
    fun getTotalEntries(): Flow<Int>

    /**
     * Retorna a quantidade de entradas agrupadas por tipo de humor.
     * Útil para gráficos de distribuição de emoções.
     */
    @Query("SELECT mood, COUNT(*) as count FROM entries GROUP BY mood")
    fun getMoodDistribution(): Flow<List<MoodCount>>
}

/**
 * Classe auxiliar para representar o resultado da contagem de humores.
 */
data class MoodCount(
    val mood: String,
    val count: Int
)
