package com.example.fitemotional.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitemotional.R
import com.example.fitemotional.ui.viewmodel.EstatisticasViewModel

@Composable
fun CPerfil(
    navController: NavController,
    viewModel: EstatisticasViewModel = viewModel()
) {
    // 🔹 Coleta dados do ViewModel
    val totalEntradas by viewModel.totalEntradas.collectAsState()
    val intensidadeMedia by viewModel.intensidadeMedia.collectAsState()
    val distribuicaoEmocoes by viewModel.distribuicaoEmocoes.collectAsState()
    val atividadesFrequentes by viewModel.atividadesFrequentes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Cabeçalho reutilizando o componente
        DiarioHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Foto de perfil centralizada
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFFB39DDB)), // placeholder roxo
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.placeholder_avatar),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Nome e email do usuário (placeholder)
        Text(
            text = "João Silva",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "joao.silva@email.com",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 Botão de editar perfil
        Button(
            onClick = { /* navegar para edição de perfil */ },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(48.dp)
                .width(200.dp)
        ) {
            Text(text = "Editar Perfil")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 🔹 Cards com dados reais do ViewModel
        PerfilCard(title = "Entradas Recentes", value = totalEntradas.toString())

        Spacer(modifier = Modifier.height(16.dp))

        PerfilCard(
            title = "Intensidade Média",
            value = String.format("%.1f", intensidadeMedia)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PerfilCard(
            title = "Atividades Mais Frequentes",
            value = if (atividadesFrequentes.isNotEmpty())
                atividadesFrequentes.entries.joinToString { "${it.key} (${it.value})" }
            else "Nenhuma atividade registrada"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PerfilCard(
            title = "Distribuição de Emoções",
            value = if (distribuicaoEmocoes.isNotEmpty())
                distribuicaoEmocoes.entries.joinToString { "${it.key} (${it.value})" }
            else "Nenhuma entrada registrada"
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PerfilCard(title: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF3E5F5), shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF5E35B1)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF311B92)
            )
        }
    }
}
