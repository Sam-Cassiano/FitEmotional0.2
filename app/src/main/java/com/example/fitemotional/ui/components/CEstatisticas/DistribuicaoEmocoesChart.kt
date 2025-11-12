package com.example.fitemotional.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun DistribuicaoEmocoesChart(
    distribuicao: Map<String, Int> // Exemplo: mapOf("Feliz" to 5, "Triste" to 2, "Neutro" to 3)
) {
    val total = distribuicao.values.sum().takeIf { it > 0 } ?: 1

    val cores = listOf(
        Color(0xFFFFC107), // Amarelo - Feliz
        Color(0xFF4DD0E1), // Azul - Neutro
        Color(0xFF66BB6A), // Verde - Calmo
        Color(0xFFEF5350), // Vermelho - Triste
        Color(0xFFBA68C8)  // Roxo - Animado
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFDFBFF),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (distribuicao.isEmpty()) {
            Text(
                text = "Nenhum dado disponível ainda 😶",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(24.dp)
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Distribuição de Emoções",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1C1C)
                    ),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // 🔹 Gráfico de Pizza
                Canvas(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(8.dp)
                ) {
                    val diameter = min(size.width, size.height)
                    var startAngle = -90f

                    distribuicao.entries.forEachIndexed { i, entry ->
                        val sweep = (entry.value.toFloat() / total) * 360f
                        drawArc(
                            color = cores[i % cores.size],
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = true,
                            size = Size(diameter, diameter)
                        )
                        startAngle += sweep
                    }
                }

                // 🔹 Legenda
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    distribuicao.entries.forEachIndexed { i, (emocao, qtd) ->
                        val percent = (qtd * 100f / total).toInt()
                        Row(
                            modifier = Modifier.padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(cores[i % cores.size], shape = RoundedCornerShape(2.dp))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "$emocao ($percent%)",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF333333)
                            )
                        }
                    }
                }
            }
        }
    }
}
