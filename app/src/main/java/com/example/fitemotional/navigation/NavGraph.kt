package com.example.fitemotional.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitemotional.ui.components.BottomBar
import com.example.fitemotional.ui.screens.AHomeDiario
import com.example.fitemotional.ui.screens.BNovaEntradaScreen
import com.example.fitemotional.ui.screens.CEstatisticas
import com.example.fitemotional.ui.screens.CPerfil
import com.example.fitemotional.ui.viewmodel.BNovaEntradaViewModel
import com.example.fitemotional.ui.viewmodel.EstatisticasViewModel
import com.example.fitemotional.ui.viewmodel.EstatisticasViewModelFactory

@Composable
fun NavGraph(
    startDestination: String = "homeDiario",
    bNovaEntradaViewModel: BNovaEntradaViewModel
) {
    val navController = rememberNavController()

    // 🔹 Cria o EstatísticasViewModel usando o mesmo DAO do BNovaEntradaViewModel
    val estatisticasViewModel: EstatisticasViewModel = viewModel(
        factory = EstatisticasViewModelFactory(
            bNovaEntradaViewModel.diaryDao
        )
    )

    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 🏠 Tela inicial do diário
            composable("homeDiario") {
                AHomeDiario(
                    navController = navController,
                    viewModel = bNovaEntradaViewModel
                )
            }

            // ✍️ Tela de nova entrada
            composable("novaEntrada") {
                BNovaEntradaScreen(viewModel = bNovaEntradaViewModel)
            }

            // 📊 Estatísticas
            composable("estatisticas") {
                CEstatisticas(
                    navController = navController,
                    viewModel = estatisticasViewModel
                )
            }

            // 👤 Perfil — agora recebe o EstatisticasViewModel
            composable("perfil") {
                CPerfil(
                    navController = navController,
                    viewModel = estatisticasViewModel
                )
            }
        }
    }
}
