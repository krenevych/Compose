package com.example.clickcounter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clickcounter.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CounterScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CounterScreen(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(12) }

    ClickCounterButton(
        count,
        clickListener = {
            count++
            Log.d("XXX", "ClickCounterButton: clicked : $count")
        },
        modifier = modifier)
}

@Composable
fun ClickCounterButton(count: Int,
                       clickListener: () -> Unit,
                       modifier: Modifier = Modifier) {



    // 2. Кнопка змінює значення стану при кліку
    Button(
        modifier = modifier.padding(100.dp),
        onClick = clickListener
    ) {
        // 3. Text зчитує стан і автоматично оновлюється при кожній зміні count
        Text(text = "Натиснуто разів: $count")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        CounterScreen()
    }
}