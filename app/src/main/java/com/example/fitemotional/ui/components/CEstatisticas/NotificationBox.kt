package com.example.fitemotional.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset

@Composable
fun NotificationBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFFFF9C4), // amarelo claro
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔹 Ícone circular com "estrelas"
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFFFEB3B), shape = CircleShape) // amarelo brilhante
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = size.minDimension / 6
                    val starOffsets = listOf(
                        Offset(center.x - radius, center.y - radius),
                        Offset(center.x + radius, center.y - radius),
                        Offset(center.x - radius / 2, center.y + radius / 2),
                        Offset(center.x + radius / 2, center.y + radius / 2)
                    )
                    starOffsets.forEach { offset ->
                        drawCircle(
                            color = Color.White,
                            radius = radius / 2,
                            center = offset
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 🔹 Texto da notificação
            Column {
                Text(
                    text = "Continue assim!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121) // cinza escuro/preto
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Você está criando um ótimo hábito de reflexão",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF212121)
                )
            }
        }
    }
}
