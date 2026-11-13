package com.example.shoplist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.shoplist.data.RepositoryImpl
import com.example.shoplist.domain.usecases.AddShopItemUseCase
import com.example.shoplist.domain.usecases.GetShopListUseCase
import com.example.shoplist.presentation.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    // Навчальний етап: створюємо репозиторій тут, пізніше додамо DI.
    private val repository = RepositoryImpl()
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ShopListScreen(
                        items = getShopListUseCase(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
