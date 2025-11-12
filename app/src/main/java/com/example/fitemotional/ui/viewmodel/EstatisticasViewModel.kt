package com.example.fitemotional.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitemotional.data.local.DiaryDao
import com.example.fitemotional.data.model.DiaryEntry
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel responsável por calcular estatísticas com base nas entradas do diário.
 * Inclui:
 * - total de entradas,
 * - intensidade média,
 * - distribuição de emoções,
 * - atividades mais frequentes.
 */
class EstatisticasViewModel(private val diaryDao: DiaryDao) : ViewModel() {

    // 🔹 Fluxo com todas as entradas do diário
    private val allEntries: Flow<List<DiaryEntry>> = diaryDao.getAllEntries()

    // 🔹 Total de entradas no diário
    val totalEntradas: StateFlow<Int> = allEntries
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    // 🔹 Intensidade média das emoções
    val intensidadeMedia: StateFlow<Double> = allEntries
        .map { entries ->
            if (entries.isNotEmpty()) entries.map { it.intensity }.average()
            else 0.0
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    // 🔹 Distribuição de emoções (ex: {"Feliz": 3, "Triste": 2, "Neutro": 1})
    val distribuicaoEmocoes: StateFlow<Map<String, Int>> = allEntries
        .map { entries ->
            entries.groupingBy { it.mood }.eachCount()
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    // 🔹 Atividades mais frequentes (ex: {"Exercício": 5, "Família": 3})
    val atividadesFrequentes: StateFlow<Map<String, Int>> = allEntries
        .map { entries ->
            entries.flatMap { it.activities }      // junta todas as atividades
                .groupingBy { it }                 // agrupa pelo nome
                .eachCount()                        // conta ocorrências
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())
}
