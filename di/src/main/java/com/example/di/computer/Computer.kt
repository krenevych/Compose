package com.example.di.computer

import javax.inject.Inject

// Інʼєкція в конструктор
class Computer @Inject constructor(
    val processor: Processor,
    val memory: Memory,
    val storage: Ssd,
) {

    @Inject
    lateinit var tower: Tower

    fun boot() {
        tower.start()
        processor.start()
        memory.initialize()
        storage.readBootData()
    }
}