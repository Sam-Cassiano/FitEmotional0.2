package com.example.fitemotional.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitemotional.data.local.DiaryDao
import com.example.fitemotional.data.model.DiaryEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel responsável por gerenciar as operações do diário.
 * Lida com a inserção, leitura e atualização das entradas no banco de dados Room.
 */
class BNovaEntradaViewModel(
    val diaryDao: DiaryDao // 🔹 público para ser usado em outras telas (ex: Estatísticas)
) : ViewModel() {

    /**
     * Fluxo com todas as entradas do diário.
     * Observado em tempo real pelas telas AHomeDiario e CEstatisticas.
     */
    val allEntries: Flow<List<DiaryEntry>> = diaryDao.getAllEntries()

    /**
     * Salva uma nova entrada no diário.
     */
    fun salvarEntrada(
        date: LocalDate,
        mood: String,
        intensity: Float,
        activities: List<String>,
        notes: String,
        gratitude: String
    ) {
        val entry = DiaryEntry(
            date = date,
            mood = mood,
            intensity = intensity,
            activities = activities,
            notes = notes,
            gratitude = gratitude
        )

        viewModelScope.launch {
            diaryDao.insert(entry)
        }
    }

    /**
     * Remove uma entrada específica do diário.
     */
    fun deletarEntrada(entry: DiaryEntry) {
        viewModelScope.launch {
            diaryDao.delete(entry)
        }
    }

    /**
     * Atualiza uma entrada existente no banco.
     */
    fun atualizarEntrada(entry: DiaryEntry) {
        viewModelScope.launch {
            diaryDao.update(entry)
        }
    }

    /**
     * Retorna as entradas filtradas por data.
     *
     * Observação: o DAO espera a data como String (formatada), então convertemos aqui.
     */
    fun getEntriesByDate(date: LocalDate): Flow<List<DiaryEntry>> {
        val dateString = date.toString() // ISO: "yyyy-MM-dd" — compatível com a query do DAO
        return diaryDao.getEntriesByDate(dateString)
    }

    /**
     * Calcula a distribuição de atividades em todas as entradas.
     * Útil para gráficos de estatísticas (ex: Atividades mais frequentes).
     */
    val atividadesFrequentes: Flow<Map<String, Int>> = allEntries.map { entries ->
        entries.flatMap { it.activities }           // Junta todas as atividades
            .groupingBy { it }                      // Agrupa pelo nome da atividade
            .eachCount()                             // Conta quantas vezes cada atividade aparece
    }
}
