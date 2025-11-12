package com.example.fitemotional.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fitemotional.data.local.DiaryDao

class EstatisticasViewModelFactory(
    private val diaryDao: DiaryDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EstatisticasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EstatisticasViewModel(diaryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
