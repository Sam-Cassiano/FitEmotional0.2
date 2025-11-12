package com.example.fitemotional.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer

@Composable
fun AtividadesFrequentesChart(
    atividades: Map<String, Int> // ex: mapOf("Família" to 2, "Exercício" to 3)
) {
    // 🔹 Converte o Map em barras do gráfico
    val barras = atividades.entries.map { (nome, valor) ->
        BarChartData.Bar(
            label = nome,
            value = valor.toFloat(),
            color = Color(0xFFBA68C8) // roxo suave
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFDFBFF),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🔹 Título com ícone
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Atividades Mais Frequentes",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 18.sp,
                        color = Color(0xFF1C1C1C)
                    )
                )
            }

            // 🔹 Gráfico de barras
            BarChart(
                barChartData = BarChartData(barras),
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
                barDrawer = SimpleBarDrawer() // apenas as barras
            )
        }
    }
}
