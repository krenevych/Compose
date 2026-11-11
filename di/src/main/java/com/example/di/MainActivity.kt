package com.example.di

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.di.computer.Computer
import com.example.di.computer.Memory
import com.example.di.computer.Processor
import com.example.di.computer.Ssd
import com.example.di.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val processor = Processor()
        val memory = Memory()
        val storage = Ssd()

       val computer = Computer(processor, memory, storage)

        setContent {
            ComposeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        computer.boot()
                    }) {
                        Text(stringResource(R.string.button_label))
                    }
                }
            }
        }
    }
}
