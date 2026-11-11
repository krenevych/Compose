package com.example.di.computer

// Інʼєкція в конструктор
class Computer(
    val processor: Processor,
    val memory: Memory,
    val storage: Ssd,
) {

    fun boot() {
        processor.start()
        memory.initialize()
        storage.readBootData()
    }
}