package com.example.fitemotional.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitemotional.ui.components.AtividadesFrequentesChart
import com.example.fitemotional.ui.screens.DiarioHeader
import com.example.fitemotional.ui.components.DistribuicaoEmocoesChart
import com.example.fitemotional.ui.components.EstatisticasCard
import com.example.fitemotional.ui.components.NotificationBox
import com.example.fitemotional.ui.viewmodel.EstatisticasViewModel

@Composable
fun CEstatisticas(
    navController: NavController,
    viewModel: EstatisticasViewModel = viewModel() // injeta automaticamente se o Factory estiver configurado
) {
    // 🔹 Coleta dos fluxos do ViewModel
    val totalEntradas by viewModel.totalEntradas.collectAsStateWithLifecycle()
    val intensidadeMedia by viewModel.intensidadeMedia.collectAsStateWithLifecycle()
    val distribuicaoEmocoes by viewModel.distribuicaoEmocoes.collectAsStateWithLifecycle()
    val atividadesFrequentes by viewModel.atividadesFrequentes.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // garante que tudo role se não couber na tela
            .padding(16.dp)
    ) {
        // 🔹 Cabeçalho
        DiarioHeader()

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Estatísticas principais
        EstatisticasCard(
            totalEntradas = totalEntradas,
            intensidadeMedia = intensidadeMedia
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Gráfico de atividades mais frequentes
        AtividadesFrequentesChart(
            atividades = atividadesFrequentes.ifEmpty {
                mapOf("Família" to 2, "Exercício" to 2, "Amigos" to 1) // placeholder
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Gráfico de emoções (usa dados reais)
        DistribuicaoEmocoesChart(
            distribuicao = distribuicaoEmocoes.ifEmpty {
                mapOf("Feliz" to 1, "Triste" to 1, "Neutro" to 1)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        NotificationBox()

        // 🔹 Texto informativo inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Análise emocional baseada nas suas entradas recentes",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}